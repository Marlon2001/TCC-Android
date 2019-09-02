package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.dao.CidadeEstadoDAO;
import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Cidade;
import godinner.lab.com.godinner.model.Contato;
import godinner.lab.com.godinner.model.Endereco;
import godinner.lab.com.godinner.model.Estado;
import godinner.lab.com.godinner.tasks.CadastroUsuario;

public class Cadastro3Activity extends AppCompatActivity {

    private TextView txtCep;
    private TextView txtNumero;
    private TextView txtReferencia;
    private TextView txtLogradouro;
    private TextView txtComplemento;
    private TextView txtBairro;
    private Spinner spinnerEstado;
    private Spinner spinnerCidade;

    private TextInputLayout txtCepLayout;
    private TextInputLayout txtNumeroLayout;
    private TextInputLayout txtReferenciaLayout;
    private TextInputLayout txtLogradouroLayout;
    private TextInputLayout txtComplementoLayout;
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
        Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.logo));

        txtCep = findViewById(R.id.txt_cep);
        txtNumero = findViewById(R.id.txt_numero);
        txtReferencia = findViewById(R.id.txt_referencia);
        txtLogradouro = findViewById(R.id.txt_logradouro);
        txtComplemento = findViewById(R.id.txt_complemento);
        txtBairro = findViewById(R.id.txt_bairro);
        spinnerEstado = findViewById(R.id.spinner_estado);
        spinnerCidade = findViewById(R.id.spinner_cidade);

        txtCepLayout = findViewById(R.id.txt_cep_layout);
        txtNumeroLayout = findViewById(R.id.txt_numero_layout);
        txtReferenciaLayout = findViewById(R.id.txt_referencia_layout);
        txtLogradouroLayout = findViewById(R.id.txt_logradouro_layout);
        txtComplementoLayout= findViewById(R.id.txt_complemento_layout);
        txtBairroLayout = findViewById(R.id.txt_bairro_layout);
//        txtCidadeLayout = findViewById(R.id.txt_cidade_layout);
//        txtEstadoLayout = findViewById(R.id.txt_estado_layout);

        btnFinalizar = findViewById(R.id.btn_finalizar);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");
        contatoIntent = (Contato) intent.getSerializableExtra("contato");

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()){
                    Endereco e = new Endereco();
                    e.setCep(txtCep.getText().toString());
                    e.setNumero(txtNumero.getText().toString());
                    e.setReferencia(txtReferencia.getText().toString());
                    e.setLogradouro(txtLogradouro.getText().toString());
                    e.setComplemento(txtComplemento.getText().toString());
                    e.setBairro(txtBairro.getText().toString());
                    Cidade cidade = (Cidade) spinnerCidade.getSelectedItem();
                    Estado estado = (Estado) spinnerEstado.getSelectedItem();
                    e.setCidade(cidade.getIdCidade());
                    e.setEstado(estado.getIdEstado());

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Cadastro3Activity.this);
                    final View mView = getLayoutInflater().inflate(R.layout.loading_dialog, null);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    try {
                        CadastroUsuario cadastroUsuario = new CadastroUsuario(getApplicationContext(), cadastroIntent, contatoIntent, e);
                        cadastroUsuario.execute();
                        cadastroUsuario.get();

                        dialog.dismiss();
                        Intent abrirBemVindo = new Intent(Cadastro3Activity.this, BemVindoActivity.class);
                        startActivity(abrirBemVindo);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } catch (ExecutionException | InterruptedException e1) {
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    Estado e = (Estado) parent.getItemAtPosition(position);
                    CidadeEstadoDAO mCidadeEstadoDAO = new CidadeEstadoDAO(Cadastro3Activity.this);
                    List cidades = mCidadeEstadoDAO.getCidadesByEstado(e.getIdEstado());

                    ArrayAdapter mAdapter = new ArrayAdapter(Cadastro3Activity.this, android.R.layout.simple_list_item_1, cidades);
                    spinnerCidade.setAdapter(mAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent abrirCadastro2 = new Intent(getApplicationContext(), Cadastro2Activiity.class);
        abrirCadastro2.putExtra("cadastro", cadastroIntent);
        abrirCadastro2.putExtra("contato", contatoIntent);
        startActivity(abrirCadastro2);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

        if(false){
            txtCidadeLayout.setErrorEnabled(true);
            txtCidadeLayout.setError("A cidade é obrigatória");
            semErro = false;
        }

        if(false){
            txtEstadoLayout.setErrorEnabled(true);
            txtEstadoLayout.setError("O estado é obrigatório");
            semErro = false;
        }

        return semErro;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CidadeEstadoDAO mCidadeEstadoDAO = new CidadeEstadoDAO(Cadastro3Activity.this);

        Estado e = new Estado();
        e.setEstado("Escolha um Estado");
        e.setIdEstado(0);

        List estados = new ArrayList();
        estados.add(e);
        estados.addAll(mCidadeEstadoDAO.getEstados());

        ArrayAdapter mAdapter = new ArrayAdapter(Cadastro3Activity.this, android.R.layout.simple_list_item_1, estados);
        spinnerEstado.setAdapter(mAdapter);
        spinnerEstado.setSelection(0);
    }
}
