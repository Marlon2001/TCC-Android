package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.RestauranteExibicao;
import godinner.lab.com.godinner.tasks.BuscarPromocoesRestaurante;

public class TelaRestaurante extends AppCompatActivity {

    private TextView txtRestaurante;
    private TextView txtPreco;
    private TextView txtEntrega;
    private TextView txtAvaliacao;

    private RecyclerView mPromocoes;
    private RecyclerView mTodosProdutos;

    public static ArrayList<Produto> mProdutosPromocao;
    public static ArrayList<Produto> mProdutosTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_restaurante);

        txtRestaurante = findViewById(R.id.nome_restaurante);
        txtPreco = findViewById(R.id.preco);
        txtEntrega = findViewById(R.id.tempo_entrega);
        txtAvaliacao = findViewById(R.id.avaliacao_restaurante);
        mPromocoes = findViewById(R.id.promocoes);

        Intent mIntent = getIntent();
        RestauranteExibicao mRestaurante = (RestauranteExibicao) mIntent.getSerializableExtra("restaurante");

        txtRestaurante.setText(mRestaurante.getRazaoSocial());
        txtPreco.setText("R$ "+mRestaurante.getPrecoEntrega());
        txtEntrega.setText(mRestaurante.getTempoEntrega());
        txtAvaliacao.setText(mRestaurante.getNota());

        LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mPromocoes.setLayoutManager(linearLayoutManagerHorizontal);

        try {
            TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(TelaRestaurante.this);
            String token = mTokenUsuarioDAO.consultarToken();
            BuscarPromocoesRestaurante mPromocoesRestaurante = new BuscarPromocoesRestaurante(mRestaurante.getId(), token);
            mPromocoesRestaurante.execute().get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
