package godinner.lab.com.godinner;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.adapter.CategoriasAdapter;
import godinner.lab.com.godinner.adapter.ListaRestaurantesAdapter;
import godinner.lab.com.godinner.adapter.RestaurantesProximosAdapter;
import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Categoria;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Restaurante;
import godinner.lab.com.godinner.model.RestauranteExibicao;
import godinner.lab.com.godinner.tasks.BuscarCategorias;
import godinner.lab.com.godinner.tasks.BuscarRestaurantesProximos;
import godinner.lab.com.godinner.tasks.RestaurantesMaisVisitados;

public class TelaInicialActivity extends AppCompatActivity {

    private RecyclerView mRestaurantesProximos;
    private RecyclerView mCategorias;
    private RecyclerView mListaRestaurantes;
    private TextView txtEnderecoEntrega;

    public static ArrayList<Categoria> categorias;
    public static ArrayList<RestauranteExibicao> restaurantes;
    public static ArrayList<RestauranteExibicao> restaurantesProximos;

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
        mCategorias.setLayoutManager(horizontalLayoutManagerCategoria);
        mListaRestaurantes.setLayoutManager(verticalLayoutManagerRestaurante);

        try {

            //buscando o consumidor logado
            ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(this);
            Consumidor c = mConsumidorDAO.consultarConsumidor();

            //buscando os restaurantes proximos de acordo com o usario logaso
            BuscarRestaurantesProximos RestaurantesProximos = new BuscarRestaurantesProximos(c.getId());
            RestaurantesProximos.execute().get();


            BuscarCategorias mBuscarCategorias = new BuscarCategorias();
            mBuscarCategorias.execute().get();

            RestaurantesMaisVisitados mRestaurantesMaisVisitados = new RestaurantesMaisVisitados();
            mRestaurantesMaisVisitados.execute();

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //inflando as listas dos resturatesproximos
        popularRestaurantesProximos(this.restaurantesProximos);

        popularCategorias(this.categorias);
        popularListaDeRestaurantes(this.restaurantesProximos);


    }

    private void popularRestaurantesProximos(ArrayList<RestauranteExibicao> restaurantes){
        RestaurantesProximosAdapter mRestaurantesProximosAdapter = new RestaurantesProximosAdapter(restaurantes, this, new RestaurantesProximosAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {
                Toast.makeText(TelaInicialActivity.this, "Clicou no restaurante "+index, Toast.LENGTH_SHORT).show();
            }
        });
        mRestaurantesProximos.setAdapter(mRestaurantesProximosAdapter);
    }
    private void popularListaDeRestaurantes(ArrayList<RestauranteExibicao> restaurantes){

    }

    private void popularCategorias(ArrayList<Categoria> categorias){

        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categorias, this, new CategoriasAdapter.CategoriaOnClickListener() {
            @Override
            public void onClickCategoria(View view, int index) {
                Toast.makeText(TelaInicialActivity.this, "Clicou id " + index , Toast.LENGTH_SHORT).show();
            }
        });
        mCategorias.setAdapter(categoriasAdapter);

    }
}
