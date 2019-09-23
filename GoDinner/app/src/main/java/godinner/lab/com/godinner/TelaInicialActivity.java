package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
            ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(this);
            Consumidor c = mConsumidorDAO.consultarConsumidor();

            txtEnderecoEntrega.setText(c.getEndereco().getLogradouro() + ", " +c.getEndereco().getNumero());

            TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(this);
            String token = mTokenUsuarioDAO.consultarToken();

            BuscarRestaurantesProximos mRestaurantesProximos = new BuscarRestaurantesProximos(2, token);
            mRestaurantesProximos.execute().get();

            BuscarCategorias mBuscarCategorias = new BuscarCategorias(token);
            mBuscarCategorias.execute().get();

//            RestaurantesMaisVisitados mRestaurantesMaisVisitados = new RestaurantesMaisVisitados(token);
//            mRestaurantesMaisVisitados.execute();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularRestaurantesProximos();
        popularCategorias();
//        popularListaDeRestaurantes();
    }

    private void popularRestaurantesProximos(){
        RestaurantesProximosAdapter mRestaurantesProximosAdapter = new RestaurantesProximosAdapter(restaurantesProximos, this, new RestaurantesProximosAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {
                Intent abrirTelaRestaurante = new Intent(TelaInicialActivity.this, TelaRestaurante.class);
                RestauranteExibicao restauranteExibicao = restaurantesProximos.get(index);

                abrirTelaRestaurante.putExtra("restaurante", restauranteExibicao);
                startActivity(abrirTelaRestaurante);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        mRestaurantesProximos.setAdapter(mRestaurantesProximosAdapter);
    }

    private void popularCategorias(){
        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categorias, this, new CategoriasAdapter.CategoriaOnClickListener() {
            @Override
            public void onClickCategoria(View view, int index) {
                Toast.makeText(TelaInicialActivity.this, "Clicou id " + index , Toast.LENGTH_SHORT).show();
            }
        });
        mCategorias.setAdapter(categoriasAdapter);
    }

    private void popularListaDeRestaurantes(){
        ListaRestaurantesAdapter mRestaurantesAdapter = new ListaRestaurantesAdapter(restaurantes, this, new ListaRestaurantesAdapter.RestauranteOnClickListener() {
            @Override
            public void onClickRestaurante(View view, int index) {
                Intent abrirTelaRestaurante = new Intent(TelaInicialActivity.this, TelaRestaurante.class);
                RestauranteExibicao restauranteExibicao = restaurantes.get(index);
                abrirTelaRestaurante.putExtra("restaurante", restauranteExibicao);
                startActivity(abrirTelaRestaurante);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        mListaRestaurantes.setAdapter(mRestaurantesAdapter);
    }
}
