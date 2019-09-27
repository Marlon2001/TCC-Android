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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;
import godinner.lab.com.godinner.utils.ValidaCampos;

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
        Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.logo));

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

                    String strCpf = txtCpf.getText().toString();
                    String cpf = strCpf.substring(0, 3) + "." + strCpf.substring(3, 6) + "." + strCpf.substring(6, 9) + "-" + strCpf.substring(9, 11);
                    con.setCpf(cpf);

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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("fecharActivity"));
    }

    @Override
    public void onBackPressed() {
        Intent abrirCadastro1 = new Intent(getApplicationContext(), Cadastro1Activity.class);
        abrirCadastro1.putExtra("cadastro", cadastroIntent);
        startActivity(abrirCadastro1);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    public boolean validarCampos(){
        boolean semErro = true;

        if(txtNome.getText().toString().trim().length() < 3){
            txtNomeLayout.setErrorEnabled(true);
            txtNomeLayout.setError("O nome é obrigatório.");
            semErro = false;
        }

        if(!ValidaCampos.isValidTelefone(txtTelefone.getText().toString())){
            txtTelefoneLayout.setErrorEnabled(true);
            txtTelefoneLayout.setError("O telefone é inválido.");
            semErro = false;
        }

        if(!ValidaCampos.isValidCpf(txtCpf.getText().toString())){
            txtCpfLayout.setErrorEnabled(true);
            txtCpfLayout.setError("O CPF é inválido.");
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
