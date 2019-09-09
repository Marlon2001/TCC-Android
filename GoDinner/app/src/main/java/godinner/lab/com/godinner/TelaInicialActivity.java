package godinner.lab.com.godinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import godinner.lab.com.godinner.adapter.CategoriasAdapter;
import godinner.lab.com.godinner.adapter.ListaRestaurantes;
import godinner.lab.com.godinner.adapter.RestaurantesProximosAdapter;
import godinner.lab.com.godinner.model.Categoria;
import godinner.lab.com.godinner.model.Restaurante;

public class TelaInicialActivity extends AppCompatActivity {

    private RecyclerView mRestaurantesProximos;
    private RecyclerView mCategorias;
    private RecyclerView mListaRestaurantes;
    private TextView txtEnderecoEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        mRestaurantesProximos = findViewById(R.id.restaurantes_proximos);
        mCategorias = findViewById(R.id.categorias);
        mListaRestaurantes = findViewById(R.id.lista_restaurantes);
        txtEnderecoEntrega = findViewById(R.id.txt_endereco_entrega);

        LinearLayoutManager horizontalLayoutManagerRestaurante = new LinearLayoutManager(TelaInicialActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagerCategoria = new LinearLayoutManager(TelaInicialActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager verticalLayoutManagerRestaurante = new LinearLayoutManager(TelaInicialActivity.this, LinearLayoutManager.VERTICAL, false);
        mRestaurantesProximos.setLayoutManager(horizontalLayoutManagerRestaurante);
        mCategorias.setLayoutManager(horizontalLayoutManagerCategoria );
        mListaRestaurantes.setLayoutManager(verticalLayoutManagerRestaurante);

        List<Restaurante> restaurantes = new ArrayList<>();

        Restaurante r = new Restaurante();
        r.setNome("Burger King");
        r.setRank(1);
        r.setAvaliacao(4.8);
        r.setDistancia(3.2);
        r.setPreco(8.00);
        r.setTempo("30 - 40");
        r.setDescricao("Lanches e Bebidas");
        r.setUrlImage("https://upload.wikimedia.org/wikipedia/pt/thumb/c/cf/Logotipo_do_Burger_King.svg/1024px-Logotipo_do_Burger_King.svg.png");
        restaurantes.add(r);

        RestaurantesProximosAdapter mAdapter = new RestaurantesProximosAdapter(restaurantes, this, new RestaurantesProximosAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {

            }
        });
        mRestaurantesProximos.setAdapter(mAdapter);

        List<Categoria> categorias = new ArrayList<>();
        Categoria c = new Categoria();
        c.setNome("Salgados");
        c.setUrlImage("https://www.bralyx.com/imagens/informacoes/maquina-fazer-salgados-valor-06.jpg");
        categorias.add(c);

        CategoriasAdapter mAdapter2 = new CategoriasAdapter(categorias, this, new CategoriasAdapter.CategoriaOnClickListener() {
            @Override
            public void onClickCategoria(View view, int index) {

            }
        });
        mCategorias.setAdapter(mAdapter2);

        Restaurante r2 = new Restaurante();
        r2.setNome("Mac Donnalds");
        r2.setRank(2);
        r2.setAvaliacao(4.5);
        r2.setDistancia(2.4);
        r2.setPreco(5.00);
        r2.setTempo("20 - 30");
        r2.setDescricao("Salgados e Bebidas");
        r2.setUrlImage("https://pbs.twimg.com/profile_images/1150268408287698945/x4f3ITmx.png");
        restaurantes.add(r2);

        ListaRestaurantes mAdapter3 = new ListaRestaurantes(restaurantes, this, new ListaRestaurantes.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {

            }
        });
        mListaRestaurantes.setAdapter(mAdapter3);
    }
}
