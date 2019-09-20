package godinner.lab.com.godinner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new RestauranteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder restauranteViewHolder, final int i) {
        Restaurante r = mRestaurantes.get(i);
        restauranteViewHolder.imgRestaurante.setImageDrawable(ContextCompat.getDrawable(context, R.color.colorWhite));
        restauranteViewHolder.progress.setVisibility(View.VISIBLE);
        restauranteViewHolder.txtRestaurante.setText(r.getRazaoSocial());

        restauranteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestauranteOnClickListener.onClickRestaurante(v, i);
            }
        });

        try{
            URL urlImage = new URL(r.getFoto());

            CarregaImage carregaImage = new CarregaImage();
            carregaImage.mViewHolder = restauranteViewHolder;
            carregaImage.execute(urlImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantes != null ? mRestaurantes.size() : 0;
    }

    public interface RestauranteOnClickListener {
        void onClickRestaurante(View view, int index);
    }

    protected class CarregaImage extends AsyncTask<URL, Void, Drawable> {

        private RestauranteViewHolder mViewHolder;

        @Override
        protected Drawable doInBackground(URL... urls) {
            URL url = urls[0];
            InputStream content;

            try {
                content = (InputStream) url.getContent();

                return Drawable.createFromStream(content, "src");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            mViewHolder.imgRestaurante.setImageDrawable(drawable);
            mViewHolder.progress.setVisibility(View.INVISIBLE);
            super.onPostExecute(drawable);
        }
    }

    protected class RestauranteViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRestaurante;
        private TextView txtRestaurante;
        private ProgressBar progress;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRestaurante = itemView.findViewById(R.id.image_item);
            txtRestaurante = itemView.findViewById(R.id.title_item);
            progress = itemView.findViewById(R.id.progressImage);
        }
    }
}
