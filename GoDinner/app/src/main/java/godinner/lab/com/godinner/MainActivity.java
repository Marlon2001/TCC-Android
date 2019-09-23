package godinner.lab.com.godinner;

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

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Login;
import godinner.lab.com.godinner.tasks.BuscarConsumidor;
import godinner.lab.com.godinner.tasks.LoginUsuario;

public class MainActivity extends AppCompatActivity {

    public static String token;
    public static String erro;
    public static Consumidor mConsumidorLogado;
    public static final String ipServidor = "10.107.144.15:8080";

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

        Glide.with(this).load(R.drawable.logo2).into((ImageView) findViewById(R.id.logo));

        btnLogar = findViewById(R.id.btn_entrar);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        loginButton = findViewById(R.id.login_button);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_password);
        txtEmailLayout = findViewById(R.id.txt_email_layout);
        txtSenhaLayout = findViewById(R.id.txt_password_layout);

        checkLoginStatus();

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erro = null;
                token = null;
                if(validarCampos()){
                    Login login = new Login();
                    login.setEmail(txtEmail.getText().toString());
                    login.setSenha(txtSenha.getText().toString());

                    try {
                        LoginUsuario mLogin = new LoginUsuario(login, MainActivity.this);
                        mLogin.execute().get();

                        if(erro != null){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Erro no login.")
                                    .setMessage("Usuário ou senha incorretos.")
                                    .setPositiveButton("Fechar", null)
                                    .show();
                        } else {
                            TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(getApplicationContext());
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
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro = new Intent(getApplicationContext(), Cadastro1Activity.class);
                startActivity(abrirCadastro);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken!=null){
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken token){
        // Enviando uma solicitação ao Facebook para pegar os dados do usuário através da API Graph
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    Log.d("NOME", first_name+" "+last_name);
                    Log.d("E-MAIL", email);
                    Log.d("IMAGE", image_url);
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
        if(AccessToken.getCurrentAccessToken()!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private boolean validarCampos(){
        boolean semErros = true;

        if(!ValidaCampos.isValidEmail(txtEmail.getText().toString())){
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("Insira um e-mail válido.");
            semErros = false;
        }

        if(txtSenha.getText().toString().length() < 6){
            txtSenhaLayout.setErrorEnabled(true);
            txtSenhaLayout.setError("Insira uma senha válida.");
            semErros = false;
        }

        return semErros;
    }
}
