package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
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
import godinner.lab.com.godinner.tasks.ConsultarCep;
import godinner.lab.com.godinner.utils.ValidaCampos;

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
    private TextInputLayout txtComplementoLayout;
    private TextInputLayout txtLogradouroLayout;
    private TextInputLayout txtBairroLayout;
    private TextView txtErrorEstado;
    private TextView txtErrorCidade;

    private ImageButton btnVoltar;
    private Button btnFinalizar;

    private Cadastro cadastroIntent;
    private Contato contatoIntent;

    public static Endereco endereco;

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
        txtComplementoLayout= findViewById(R.id.txt_complemento_layout);
        txtLogradouroLayout = findViewById(R.id.txt_logradouro_layout);
        txtBairroLayout = findViewById(R.id.txt_bairro_layout);
        txtErrorEstado = findViewById(R.id.txt_error_estado);
        txtErrorCidade = findViewById(R.id.txt_error_cidade);

        btnFinalizar = findViewById(R.id.btn_finalizar);
        btnVoltar = findViewById(R.id.btn_voltar);

        Intent intent = getIntent();
        cadastroIntent = (Cadastro) intent.getSerializableExtra("cadastro");
        contatoIntent = (Contato) intent.getSerializableExtra("contato");

        txtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtCep.getText().length() == 8) {
                        try {
                            ConsultarCep consultarCep = new ConsultarCep(txtCep.getText().toString());
                            consultarCep.execute().get();

                            txtLogradouro.setText(endereco.getLogradouro());
                            txtBairro.setText(endereco.getBairro());

                            Estado e = new Estado();
                            e.setEstado("Escolha um Estado");
                            e.setIdEstado(0);
                            Estado e2 = new Estado();
                            e2.setEstado(endereco.getEstadoNome());
                            e2.setIdEstado(endereco.getIdEstado());

                            List estados = new ArrayList();
                            estados.add(e);
                            estados.add(e2);
                            ArrayAdapter mAdapter = new ArrayAdapter(Cadastro3Activity.this, android.R.layout.simple_list_item_1, estados);

                            Cidade c = new Cidade();
                            c.setCidade("Escolha uma Cidade");
                            c.setIdCidade(0);
                            Cidade c2 = new Cidade();
                            c2.setCidade(endereco.getCidadeNome());
                            c2.setIdCidade(endereco.getIdCidade());

                            List cidades = new ArrayList();
                            cidades.add(e);
                            cidades.add(c2);
                            ArrayAdapter mAdapter2 = new ArrayAdapter(Cadastro3Activity.this, android.R.layout.simple_list_item_1, cidades);

                            spinnerEstado.setAdapter(mAdapter);
                            spinnerEstado.setSelection(1);
                            spinnerCidade.setAdapter(mAdapter2);
                            spinnerCidade.setSelection(1);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

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
                    e.setIdCidade(cidade.getIdCidade());
                    e.setIdEstado(estado.getIdEstado());

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Cadastro3Activity.this);
                    final View mView = getLayoutInflater().inflate(R.layout.loading_dialog, null);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    try {
                        CadastroUsuario cadastroUsuario = new CadastroUsuario(cadastroIntent, contatoIntent, e);
                        cadastroUsuario.execute();
                        cadastroUsuario.get();

                        dialog.dismiss();
                        Intent abrirBemVindo = new Intent(Cadastro3Activity.this, BemVindoActivity.class);
                        startActivity(abrirBemVindo);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        LocalBroadcastManager.getInstance(Cadastro3Activity.this).sendBroadcast(new Intent("fecharActivity"));
                        finish();
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

        if(!ValidaCampos.isValidCep(txtCep.getText().toString())){
            txtCepLayout.setErrorEnabled(true);
            txtCepLayout.setError("O cep é inválido.");
            semErro = false;
        }

        if(txtNumero.getText().toString().trim().isEmpty()){
            txtNumeroLayout.setErrorEnabled(true);
            txtNumeroLayout.setError("O número é obrigatório.");
            semErro = false;
        }

        if(txtLogradouro.getText().toString().trim().isEmpty()){
            txtLogradouroLayout.setErrorEnabled(true);
            txtLogradouroLayout.setError("O logradouro é obrigatório.");
            semErro = false;
        }

        if(txtBairro.getText().toString().trim().isEmpty()){
            txtBairroLayout.setErrorEnabled(true);
            txtBairroLayout.setError("O bairro é obrigatório.");
            semErro = false;
        }

        if(ValidaCampos.isValidEstado(spinnerEstado.getSelectedItem())){
            txtErrorEstado.setVisibility(View.VISIBLE);
            txtErrorEstado.setText("Escolha um estado.");
            semErro = false;
        }

        if(ValidaCampos.isValidCidade(spinnerCidade.getSelectedItem())){
            txtErrorCidade.setVisibility(View.VISIBLE);
            txtErrorCidade.setText("Escolha uma cidade.");
            semErro = false;
        }

        return semErro;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        CidadeEstadoDAO mCidadeEstadoDAO = new CidadeEstadoDAO(Cadastro3Activity.this);
//
//        Estado e = new Estado();
//        e.setEstado("Escolha um Estado");
//        e.setIdEstado(0);
//
//        List estados = new ArrayList();
//        estados.add(e);
//        estados.addAll(mCidadeEstadoDAO.getEstados());
//
//        ArrayAdapter mAdapter2 = new ArrayAdapter(Cadastro3Activity.this, android.R.layout.simple_list_item_1, estados);
//        spinnerEstado.setAdapter(mAdapter2);
//        spinnerEstado.setSelection(0);
    }
}
