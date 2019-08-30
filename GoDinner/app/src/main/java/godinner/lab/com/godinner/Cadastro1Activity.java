package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;

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
                finish();
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent abrirMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(abrirMainActivity);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        super.onBackPressed();
    }

    public boolean validarCampos(){
        boolean semErro = true;

        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("O e-mail é obrigatório");
            semErro = false;
        }

        if(txtSenha.getText().toString().trim().isEmpty()){
            txtSenhaLayout.setErrorEnabled(true);
            txtSenhaLayout.setError("A senha é obrigatória");
            semErro = false;
        }

        return semErro;
    }
}
