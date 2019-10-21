package godinner.lab.com.godinner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import godinner.lab.com.godinner.model.Restaurante;
import godinner.lab.com.godinner.model.RestauranteExibicao;

public class RestaurantesProximosAdapter extends RecyclerView.Adapter<RestaurantesProximosAdapter.RestauranteViewHolder> {

    private List<RestauranteExibicao> mRestaurantes;
    private Context context;
    private RestauranteOnClickListener mRestauranteOnClickListener;

    public RestaurantesProximosAdapter(List<RestauranteExibicao> mRestaurantes, Context context, RestauranteOnClickListener mRestauranteOnClickListener) {
        this.mRestaurantes = mRestaurantes;
        this.context = context;
        this.mRestauranteOnClickListener = mRestauranteOnClickListener;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.lista_image, viewGroup, false);
        return new RestauranteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder restauranteViewHolder, final int i) {
        Restaurante r = mRestaurantes.get(i);

        restauranteViewHolder.txtRestaurante.setText(r.getRazaoSocial());
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
        private ImageView imgRestaurante;
        private TextView txtRestaurante;


        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRestaurante = itemView.findViewById(R.id.image_item);
            txtRestaurante = itemView.findViewById(R.id.title_item);
        }
    }
}
