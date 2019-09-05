package godinner.lab.com.godinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import godinner.lab.com.godinner.adapter.CategoriasAdapter;
import godinner.lab.com.godinner.adapter.RestaurantesProximosAdapter;
import godinner.lab.com.godinner.model.Categoria;
import godinner.lab.com.godinner.model.Restaurante;

public class TelaInicialActivity extends AppCompatActivity {

    private RecyclerView mRestaurantesProximos;
    private RecyclerView mCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        mRestaurantesProximos = findViewById(R.id.restaurantes_proximos);
        mCategorias = findViewById(R.id.categorias);

        LinearLayoutManager horizontalLayoutManagerRestaurante = new LinearLayoutManager(TelaInicialActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagerCategoria = new LinearLayoutManager(TelaInicialActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRestaurantesProximos.setLayoutManager(horizontalLayoutManagerRestaurante);
        mCategorias.setLayoutManager(horizontalLayoutManagerCategoria );

        List<Restaurante> restaurantes = new ArrayList<>();

        Restaurante r = new Restaurante();
        r.setNome("Burger King");
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
    }
}
