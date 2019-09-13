package godinner.lab.com.godinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.adapter.CategoriasAdapter;
import godinner.lab.com.godinner.adapter.ListaRestaurantesAdapter;
import godinner.lab.com.godinner.adapter.RestaurantesProximosAdapter;
import godinner.lab.com.godinner.model.Categoria;
import godinner.lab.com.godinner.model.Restaurante;
import godinner.lab.com.godinner.tasks.BuscarCategorias;
import godinner.lab.com.godinner.tasks.BuscarRestaurantesProximos;

public class TelaInicialActivity extends AppCompatActivity {

    private RecyclerView mRestaurantesProximos;
    private RecyclerView mCategorias;
    private RecyclerView mListaRestaurantes;
    private TextView txtEnderecoEntrega;

    public static ArrayList<Categoria> categorias;
    public static ArrayList<Restaurante> restaurantes;
    public static ArrayList<Restaurante> restaurantesProximos;

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

        try {
            BuscarRestaurantesProximos RestaurantesProximos = new BuscarRestaurantesProximos();
            RestaurantesProximos.execute().get();

            BuscarCategorias mBuscarCategorias = new BuscarCategorias();
            mBuscarCategorias.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RestaurantesProximosAdapter mAdapter1 = new RestaurantesProximosAdapter(restaurantes, this, new RestaurantesProximosAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {

            }
        });
        CategoriasAdapter mAdapter2 = new CategoriasAdapter(categorias, this, new CategoriasAdapter.CategoriaOnClickListener() {
            @Override
            public void onClickCategoria(View view, int index) {

            }
        });
        ListaRestaurantesAdapter mAdapter3 = new ListaRestaurantesAdapter(restaurantes, this, new ListaRestaurantesAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {

            }
        });

        mRestaurantesProximos.setAdapter(mAdapter1);
        mCategorias.setAdapter(mAdapter2);
        mListaRestaurantes.setAdapter(mAdapter3);
    }
}
