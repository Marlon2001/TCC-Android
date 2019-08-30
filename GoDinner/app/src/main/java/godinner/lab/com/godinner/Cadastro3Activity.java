package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;
import godinner.lab.com.godinner.model.Endereco;
import godinner.lab.com.godinner.tasks.CadastroUsuario;

public class Cadastro3Activity extends AppCompatActivity {

    private TextView txtCep;
    private TextView txtNumero;
    private TextView txtReferencia;
    private TextView txtLogradouro;
    private TextView txtBairro;
    private TextView txtCidade;
    private TextView txtEstado;

    private TextInputLayout txtCepLayout;
    private TextInputLayout txtNumeroLayout;
    private TextInputLayout txtReferenciaLayout;
    private TextInputLayout txtLogradouroLayout;
    private TextInputLayout txtBairroLayout;
    private TextInputLayout txtCidadeLayout;
    private TextInputLayout txtEstadoLayout;

    private ImageButton btnVoltar;
    private Button btnFinalizar;

    private Cadastro cadastroIntent;
    private Contato contatoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro3);

        txtCep = findViewById(R.id.txt_cep);
        txtNumero = findViewById(R.id.txt_numero);
        txtReferencia = findViewById(R.id.txt_referencia);
        txtLogradouro = findViewById(R.id.txt_logradouro);
        txtBairro = findViewById(R.id.txt_bairro);
        txtCidade = findViewById(R.id.txt_cidade);
        txtEstado = findViewById(R.id.txt_estado);

        txtCepLayout = findViewById(R.id.txt_cep_layout);
        txtNumeroLayout = findViewById(R.id.txt_numero_layout);
        txtReferenciaLayout = findViewById(R.id.txt_referencia_layout);
        txtLogradouroLayout = findViewById(R.id.txt_logradouro_layout);
        txtBairroLayout = findViewById(R.id.txt_bairro_layout);
        txtCidadeLayout = findViewById(R.id.txt_cidade_layout);
        txtEstadoLayout = findViewById(R.id.txt_estado_layout);

        btnFinalizar = findViewById(R.id.btn_finalizar);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");
        contatoIntent = (Contato) intent.getSerializableExtra("contato");

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()){
//                    Intent enviarDados = new Intent(getApplicationContext(), null);

                    Endereco e = new Endereco();
                    e.setCep(txtCep.getText().toString());
                    e.setNumero(txtNumero.getText().toString());
                    e.setReferencia(txtReferencia.getText().toString());
                    e.setLogradouro(txtLogradouro.getText().toString());
                    e.setBairro(txtBairro.getText().toString());
                    e.setCidade(txtCidade.getText().toString());
                    e.setEstado(txtEstado.getText().toString());

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
                    final View mView = getLayoutInflater().inflate(R.layout.loading_dialog, null);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    try {
                        CadastroUsuario cadastroUsuario = new CadastroUsuario(getApplicationContext(), cadastroIntent, contatoIntent, e);
                        cadastroUsuario.execute();
                        cadastroUsuario.get();

                        dialog.dismiss();
                        Intent abrirBemVindo = new Intent(getApplicationContext(), null);
                        startActivity(abrirBemVindo);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro2 = new Intent(getApplicationContext(), Cadastro2Activiity.class);
                abrirCadastro2.putExtra("cadastro", cadastroIntent);
                abrirCadastro2.putExtra("contato", contatoIntent);
                startActivity(abrirCadastro2);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent abrirCadastro2 = new Intent(getApplicationContext(), Cadastro2Activiity.class);
        abrirCadastro2.putExtra("cadastro", cadastroIntent);
        abrirCadastro2.putExtra("contato", contatoIntent);
        startActivity(abrirCadastro2);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        super.onBackPressed();
    }


    public boolean validarCampos(){
        boolean semErro = true;

        if(txtCep.getText().toString().trim().isEmpty()){
            txtCepLayout.setErrorEnabled(true);
            txtCepLayout.setError("O cep é obrigatório");
            semErro = false;
        }

        if(txtNumero.getText().toString().trim().isEmpty()){
            txtNumeroLayout.setErrorEnabled(true);
            txtNumeroLayout.setError("O número é obrigatório");
            semErro = false;
        }

        if(txtReferencia.getText().toString().trim().isEmpty()){
            txtReferenciaLayout.setErrorEnabled(true);
            txtReferenciaLayout.setError("O ponto de referência é obrigatório");
            semErro = false;
        }

        if(txtLogradouro.getText().toString().trim().isEmpty()){
            txtLogradouroLayout.setErrorEnabled(true);
            txtLogradouroLayout.setError("O logradouro é obrigatório");
            semErro = false;
        }

        if(txtBairro.getText().toString().trim().isEmpty()){
            txtBairroLayout.setErrorEnabled(true);
            txtBairroLayout.setError("O bairro é obrigatório");
            semErro = false;
        }

        if(txtCidade.getText().toString().trim().isEmpty()){
            txtCidadeLayout.setErrorEnabled(true);
            txtCidadeLayout.setError("A cidade é obrigatória");
            semErro = false;
        }

        if(txtEstado.getText().toString().trim().isEmpty()){
            txtEstadoLayout.setErrorEnabled(true);
            txtEstadoLayout.setError("O estado é obrigatório");
            semErro = false;
        }

        return semErro;
    }
}
