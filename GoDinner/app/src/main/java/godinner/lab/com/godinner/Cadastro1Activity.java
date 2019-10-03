package godinner.lab.com.godinner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.utils.ValidaCampos;

public class Cadastro1Activity extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtSenha;
    private TextInputLayout txtEmailLayout;
    private TextInputLayout txtSenhaLayout;

    private Button btnProximo;
    private ImageButton btnVoltar;

    private Cadastro cadastroIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);
        Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.logo));

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
        txtEmailLayout = findViewById(R.id.txt_email_layout);
        txtSenhaLayout = findViewById(R.id.txt_senha_layout);

        btnProximo = findViewById(R.id.btn_proximo);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");

        if(cadastroIntent != null){
            txtEmail.setText(cadastroIntent.getEmail());
            txtSenha.setText(cadastroIntent.getSenha());
        }

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()){
                    Intent abrirCadastro2 = new Intent(getApplicationContext(), Cadastro2Activiity.class);

                    Cadastro c = new Cadastro();
                    c.setEmail(txtEmail.getText().toString());
                    c.setSenha(txtSenha.getText().toString());

                    abrirCadastro2.putExtra("cadastro", c);

                    startActivity(abrirCadastro2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(abrirMainActivity);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("fecharActivity"));
    }

    @Override
    public void onBackPressed() {
        Intent abrirMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(abrirMainActivity);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    public boolean validarCampos(){
        boolean semErro = true;

        if(!ValidaCampos.isValidEmail(txtEmail.getText().toString())){
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("E-mail inválido.");
            semErro = false;
        }

        if(txtSenha.getText().toString().trim().length() < 8){
            txtSenhaLayout.setErrorEnabled(true);
            txtSenhaLayout.setError("A senha deve conter no minímo 8 caracteres.");
            semErro = false;
        }

        return semErro;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
