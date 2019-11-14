package godinner.lab.com.godinner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Login;
import godinner.lab.com.godinner.tasks.BuscarConsumidor;
import godinner.lab.com.godinner.tasks.LoginUsuario;
import godinner.lab.com.godinner.tasks.ValidarDadosCadastro;
import godinner.lab.com.godinner.utils.OnSingleClickListener;
import godinner.lab.com.godinner.utils.TrustCertificates;
import godinner.lab.com.godinner.utils.ValidaCampos;

public class MainActivity extends AppCompatActivity {

    public static final String ipServidor = "https://godinner.tk:8080";
    public static final String ipServidorSocket = "http://godinner.tk:3100";
    public static final String ipServidorChat = "https://godinner.tk:3200";
    public static String token = null;
    public static String erro;
    public static Consumidor mConsumidorLogado;
    public static String fotoLanchePadrao = "/restaurante/produto/1569953042416-115-840x560.jpg";
    public static String ipServidorFotos = "http://fotos.godinner.tk";
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken != null) {
                loadUserProfile(currentAccessToken);
            }
        }
    };
    private MaterialButton btnLogar;
    private MaterialButton btnCadastrar;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextInputLayout txtEmailLayout;
    private TextInputLayout txtSenhaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.scrollView).requestFocus();
        Glide.with(this).load(R.drawable.logo2).into((ImageView) findViewById(R.id.logo));

        btnLogar = findViewById(R.id.btn_logar);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        loginButton = findViewById(R.id.login_button);

        checkLoginStatus();
        TrustCertificates.trustEveryone();

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_password);
        txtEmailLayout = findViewById(R.id.txt_email_layout);
        txtSenhaLayout = findViewById(R.id.txt_password_layout);

        final TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(this);

        btnLogar.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                erro = null;
                token = null;
                if (validarCampos()) {
                    Login login = new Login();
                    login.setEmail(txtEmail.getText().toString());
                    login.setSenha(txtSenha.getText().toString());

                    try {
                        LoginUsuario mLogin = new LoginUsuario(login, MainActivity.this);
                        mLogin.execute().get();

                        if (erro != null || token == null) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Não foi desta vez.")
                                    .setMessage("Usuário ou senha incorretos.")
                                    .setPositiveButton("Fechar", null)
                                    .show();
                        } else {
                            mTokenUsuarioDAO.salvarToken(token);

                            BuscarConsumidor mBuscarConsumidor = new BuscarConsumidor(token);
                            mBuscarConsumidor.execute().get();

                            ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(getApplicationContext());
                            mConsumidorDAO.salvarConsumidorLogado(mConsumidorLogado);

                            Intent abrirTelaInicial = new Intent(getApplicationContext(), TelaInicialActivity.class);
                            startActivity(abrirTelaInicial);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            MainActivity.this.finish();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                reset();
            }
        });

        btnCadastrar.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent abrirCadastro = new Intent(getApplicationContext(), Cadastro1Activity.class);
                startActivity(abrirCadastro);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                reset();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUserProfile(AccessToken token) {
        // Enviando uma solicitação ao Facebook para pegar os dados do usuário através da API Graph
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    final String first_name = object.getString("first_name");
                    final String last_name = object.getString("last_name");
                    final String email = object.getString("email");
                    final String id = object.getString("id");
                    final String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    ValidarDadosCadastro mValidarDadosCadastro = new ValidarDadosCadastro("email", email, new ValidarDadosCadastro.ValidarCampo() {
                        @Override
                        public void Request(Boolean result) {
                            if(!result){
                                Login login = new Login();
                                login.setEmail(email);
                                login.setSenha(id + "_consumidor");

                                LoginUsuario mLogin = new LoginUsuario(login, MainActivity.this);
                                // Vai para login
                                // criar task de login por facebook
                                // a task vai validar se o email nao possui senha no cadastro do banco
                            }else{
                                Cadastro cadastro = new Cadastro();
                                cadastro.setNome(String.format("%s %s", first_name, last_name));
                                cadastro.setEmail(email);
                                cadastro.setFoto(image_url);
                                cadastro.setSenha(id + "_consumidor");

                                Intent abrirCadastro = new Intent(MainActivity.this, Cadastro2Activity.class);
                                abrirCadastro.putExtra("cadastro", cadastro);
                                abrirCadastro.putExtra("type", "facebook");

                                startActivity(abrirCadastro);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }
                    });
                    mValidarDadosCadastro.execute().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();

        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            AccessToken.setCurrentAccessToken(null);
            //loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private boolean validarCampos() {
        boolean semErros = true;

        if (!ValidaCampos.isValidEmail(txtEmail.getText().toString())) {
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("Insira um e-mail válido.");
            semErros = false;
        }

        if (txtSenha.getText().toString().length() < 6) {
            txtSenhaLayout.setErrorEnabled(true);
            txtSenhaLayout.setError("Insira uma senha válida.");
            semErros = false;
        }

        return semErros;
    }
}
