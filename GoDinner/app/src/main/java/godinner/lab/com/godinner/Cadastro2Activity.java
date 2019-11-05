package godinner.lab.com.godinner;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.santalu.maskedittext.MaskEditText;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;
import godinner.lab.com.godinner.tasks.ValidarEmailCpf;
import godinner.lab.com.godinner.utils.ValidaCampos;

public class Cadastro2Activity extends AppCompatActivity {

    private EditText txtNome;
    private MaskEditText txtCelular;
    private MaskEditText txtCpf;
    private TextInputLayout txtNomeLayout;
    private TextInputLayout txtTelefoneLayout;
    private TextInputLayout txtCpfLayout;
    private CheckBox rdoNotificacoes;
    private Button btnProximo;
    private ImageButton btnVoltar;

    private Cadastro cadastroIntent;
    private Contato contatoIntent;
    private String tipoCadastro;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.logo));

        txtNome = findViewById(R.id.txt_nome);
        txtCelular = findViewById(R.id.txt_celular);
        txtCpf = findViewById(R.id.txt_cpf);
        txtNomeLayout = findViewById(R.id.txt_nome_layout);
        txtTelefoneLayout = findViewById(R.id.txt_telefone_layout);
        txtCpfLayout = findViewById(R.id.txt_cpf_layout);
        rdoNotificacoes = findViewById(R.id.rdo1);

        btnProximo = findViewById(R.id.btn_proximo);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");
        contatoIntent = (Contato) intent.getSerializableExtra("contato");
        tipoCadastro = intent.getStringExtra("type");

        if (tipoCadastro.equals("facebook")) {
            txtNome.setText(cadastroIntent.getNome());
        }

        if (contatoIntent != null) {
            txtNome.setText(contatoIntent.getNome());
            txtCelular.setText(contatoIntent.getTelefone());
            txtCpf.setText(contatoIntent.getCpf());
            rdoNotificacoes.setChecked(contatoIntent.getNotificacoes());
        }

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    ValidarEmailCpf mValidarEmailCpf = new ValidarEmailCpf("cpf", txtCpf.getRawText(), new ValidarEmailCpf.ValidarCampo() {
                        @Override
                        public void Request(Boolean result) {
                            if (!result) {
                                txtCpfLayout.setErrorEnabled(true);
                                txtCpfLayout.setError("Cpf já cadastrado.");
                            } else {
                                txtCpfLayout.setErrorEnabled(false);

                                Intent abrirCadastro3 = new Intent(getApplicationContext(), Cadastro3Activity.class);

                                Contato con = new Contato();
                                con.setNome(txtNome.getText().toString());
                                con.setTelefone(Objects.requireNonNull(txtCelular.getText()).toString());
                                con.setCpf(Objects.requireNonNull(txtCpf.getText()).toString());
                                con.setNotificacoes(rdoNotificacoes.isChecked());
                                abrirCadastro3.putExtra("cadastro", cadastroIntent);
                                abrirCadastro3.putExtra("contato", con);
                                abrirCadastro3.putExtra("type", tipoCadastro);

                                startActivity(abrirCadastro3);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }
                    });
                    try {
                        mValidarEmailCpf.execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoCadastro.equals("facebook")) {
                    new AlertDialog.Builder(Cadastro2Activity.this)
                            .setTitle("Confirmação")
                            .setMessage("Deseja cancelar o cadastro?")
                            .setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent fechar = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(fechar);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    Cadastro2Activity.this.finish();
                                }
                            })
                            .setNegativeButton("Continuar", null)
                            .show();
                } else {
                    Intent abrirCadastro1 = new Intent(getApplicationContext(), Cadastro1Activity.class);
                    abrirCadastro1.putExtra("cadastro", cadastroIntent);
                    startActivity(abrirCadastro1);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("fecharActivity"));
    }

    @Override
    public void onBackPressed() {
        if (tipoCadastro.equals("facebook")) {
            new AlertDialog.Builder(Cadastro2Activity.this)
                    .setTitle("Confirmação")
                    .setMessage("Deseja cancelar o cadastro?")
                    .setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent fechar = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(fechar);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            Cadastro2Activity.this.finish();
                        }
                    })
                    .setNegativeButton("Continuar", null)
                    .show();
        } else {
            Intent abrirCadastro1 = new Intent(getApplicationContext(), Cadastro1Activity.class);
            abrirCadastro1.putExtra("cadastro", cadastroIntent);
            startActivity(abrirCadastro1);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        super.onBackPressed();
    }

    public boolean validarCampos() {
        boolean semErro = true;

        if (txtNome.getText().toString().trim().length() < 3) {
            txtNomeLayout.setErrorEnabled(true);
            txtNomeLayout.setError("O nome é obrigatório.");
            semErro = false;
        } else {
            txtNomeLayout.setErrorEnabled(false);
        }

        if (!ValidaCampos.isValidTelefone(Objects.requireNonNull(txtCelular.getText()).toString())) {
            txtTelefoneLayout.setErrorEnabled(true);
            txtTelefoneLayout.setError("O celular é inválido.");
            semErro = false;
        } else {
            txtTelefoneLayout.setErrorEnabled(false);
        }

        if (!ValidaCampos.isValidCpf(Objects.requireNonNull(txtCpf.getText()).toString().replace(".", "").replace("-", ""))) {
            txtCpfLayout.setErrorEnabled(true);
            txtCpfLayout.setError("O CPF é inválido.");
            semErro = false;
        } else {
            txtCpfLayout.setErrorEnabled(false);
        }

        return semErro;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
