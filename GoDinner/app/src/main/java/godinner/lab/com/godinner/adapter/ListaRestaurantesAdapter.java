package godinner.lab.com.godinner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.R;
import godinner.lab.com.godinner.model.RestauranteExibicao;

public class ListaRestaurantesAdapter extends RecyclerView.Adapter<ListaRestaurantesAdapter.RestauranteViewHolder> {

    private List<RestauranteExibicao> mRestaurantes;
    private Context context;
    private RestauranteOnClickListener mRestauranteOnClickListener;

    public ListaRestaurantesAdapter(List<RestauranteExibicao> mRestaurantes, Context context, RestauranteOnClickListener mRestauranteOnClickListener) {
        this.mRestaurantes = mRestaurantes;
        this.context = context;
        this.mRestauranteOnClickListener = mRestauranteOnClickListener;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.lista_restaurantes, viewGroup, false);
        return new RestauranteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder restauranteViewHolder, final int i) {
        RestauranteExibicao r = mRestaurantes.get(i);

        restauranteViewHolder.imgRestaurante.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chef));
        restauranteViewHolder.nome.setText(r.getRazaoSocial());
        if (r.getNota() != null) {
            restauranteViewHolder.rank.setText(r.getNota() + "º mais visitado.");
        }
        restauranteViewHolder.avaliacao.setText(r.getNota().toString());
        restauranteViewHolder.distancia.setText(r.getDistancia());
        restauranteViewHolder.preco.setText("R$ " + r.getNota());
        restauranteViewHolder.tempo.setText(r.getTempoEntrega());
        restauranteViewHolder.imgRestaurante.setImageDrawable(ContextCompat.getDrawable(context, R.color.colorWhite));

        restauranteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestauranteOnClickListener.onClickRestaurante(v, i);
            }
        });

        Picasso.get().load(MainActivity.ipServidorFotos + r.getFoto()).resize(100, 100).into(restauranteViewHolder.imgRestaurante);
    }

    @Override
    public int getItemCount() {
        return mRestaurantes != null ? mRestaurantes.size() : 0;
    }

    public interface RestauranteOnClickListener {
        void onClickRestaurante(View view, int index);
    }

    protected class RestauranteViewHolder extends RecyclerView.ViewHolder {
        private TextView nome;
        private TextView rank;
        private TextView avaliacao;

        private TextView distancia;
        private TextView preco;
        private TextView tempo;
        private ImageView imgRestaurante;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_restaurante);
            rank = itemView.findViewById(R.id.rank_restaurante);
            avaliacao = itemView.findViewById(R.id.avaliacao_restaurante);
            distancia = itemView.findViewById(R.id.distancia);
            preco = itemView.findViewById(R.id.preco);
            tempo = itemView.findViewById(R.id.tempo);
            imgRestaurante = itemView.findViewById(R.id.image);
        }
    }
}
