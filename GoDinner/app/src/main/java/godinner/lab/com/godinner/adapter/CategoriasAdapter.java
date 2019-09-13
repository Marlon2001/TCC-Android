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
import godinner.lab.com.godinner.model.Categoria;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriasViewholder> {

    private List<Categoria> mCategorias;
    private Context context;
    private CategoriaOnClickListener mCategoriaOnClickListener;

    public CategoriasAdapter(List<Categoria> mCategorias, Context context, CategoriaOnClickListener mCategoriaOnClickListener) {
        this.mCategorias = mCategorias;
        this.context = context;
        this.mCategoriaOnClickListener = mCategoriaOnClickListener;
    }

    @NonNull
    @Override
    public CategoriasViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new CategoriasViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasViewholder categoriasViewholder, final int i) {
        Categoria c = mCategorias.get(i);
        categoriasViewholder.imgCategoria.setImageDrawable(ContextCompat.getDrawable(context, R.color.colorWhite));
        categoriasViewholder.progress.setVisibility(View.VISIBLE);
        categoriasViewholder.txtCategoria.setText(c.getNome());

        categoriasViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoriaOnClickListener.onClickCategoria(v, i);
            }
        });

        try{
            URL urlImage = new URL(c.getUrlImage());

            CarregaImage carregaImage = new CarregaImage();
            carregaImage.mViewHolder = categoriasViewholder;
            carregaImage.execute(urlImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCategorias != null ? mCategorias.size() : 0;
    }

    public interface CategoriaOnClickListener {
        void onClickCategoria(View view, int index);
    }

    protected class CarregaImage extends AsyncTask<URL, Void, Drawable> {

        private CategoriasViewholder mViewHolder;

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
            mViewHolder.imgCategoria.setImageDrawable(drawable);
            mViewHolder.progress.setVisibility(View.INVISIBLE);
            super.onPostExecute(drawable);
        }
    }

    protected class CategoriasViewholder extends RecyclerView.ViewHolder {

        private ImageView imgCategoria;
        private TextView txtCategoria;
        private ProgressBar progress;

        private CategoriasViewholder(@NonNull View itemView) {
            super(itemView);

            imgCategoria = itemView.findViewById(R.id.image_item);
            txtCategoria = itemView.findViewById(R.id.title_item);
            progress = itemView.findViewById(R.id.progressImage);
        }
    }
}
