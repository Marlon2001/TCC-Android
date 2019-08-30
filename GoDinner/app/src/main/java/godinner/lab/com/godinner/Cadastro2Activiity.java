package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;

public class Cadastro2Activiity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtTelefone;
    private TextView txtCpf;
    private TextInputLayout txtNomeLayout;
    private TextInputLayout txtTelefoneLayout;
    private TextInputLayout txtCpfLayout;
    private CheckBox rdoNotificacoes;
    private Button btnProximo;
    private ImageButton btnVoltar;

    private Cadastro cadastroIntent;
    private Contato contatoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        txtNome = findViewById(R.id.txt_nome);
        txtTelefone = findViewById(R.id.txt_telefone);
        txtCpf = findViewById(R.id.txt_cpf);
        txtNomeLayout = findViewById(R.id.txt_nome_layout);
        txtTelefoneLayout = findViewById(R.id.txt_telefone_layout);
        txtCpfLayout= findViewById(R.id.txt_cpf_layout);
        rdoNotificacoes = findViewById(R.id.rdo1);

        btnProximo = findViewById(R.id.btn_proximo);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");
        contatoIntent = (Contato) intent.getSerializableExtra("contato");

        if(contatoIntent != null){
            txtNome.setText(contatoIntent.getNome());
            txtTelefone.setText(contatoIntent.getTelefone());
            txtCpf.setText(contatoIntent.getCpf());
            rdoNotificacoes.setChecked(contatoIntent.getNotificacoes());
        }

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()){
                    Intent abrirCadastro3 = new Intent(getApplicationContext(), Cadastro3Activity.class);

                    Contato con = new Contato();
                    con.setNome(txtNome.getText().toString());
                    con.setTelefone(txtTelefone.getText().toString());
                    con.setCpf(txtCpf.getText().toString());
                    con.setNotificacoes(rdoNotificacoes.isChecked());

                    abrirCadastro3.putExtra("cadastro", cadastroIntent);
                    abrirCadastro3.putExtra("contato", con);

                    startActivity(abrirCadastro3);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro1 = new Intent(getApplicationContext(), Cadastro1Activity.class);
                abrirCadastro1.putExtra("cadastro", cadastroIntent);
                startActivity(abrirCadastro1);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent abrirCadastro1 = new Intent(getApplicationContext(), Cadastro1Activity.class);
        abrirCadastro1.putExtra("cadastro", cadastroIntent);
        startActivity(abrirCadastro1);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        super.onBackPressed();
    }

    public boolean validarCampos(){
        boolean semErro = true;

        if(txtNome.getText().toString().trim().isEmpty()){
            txtNomeLayout.setErrorEnabled(true);
            txtNomeLayout.setError("O nome é obrigatório");
            semErro = false;
        }

        if(txtTelefone.getText().toString().trim().isEmpty()){
            txtTelefoneLayout.setErrorEnabled(true);
            txtTelefoneLayout.setError("O telefone é obrigatório");
            semErro = false;
        }

        if(txtCpf.getText().toString().trim().isEmpty()){
            txtCpfLayout.setErrorEnabled(true);
            txtCpfLayout.setError("O cpf é obrigatório");
            semErro = false;
        }

        return semErro;
    }
}
