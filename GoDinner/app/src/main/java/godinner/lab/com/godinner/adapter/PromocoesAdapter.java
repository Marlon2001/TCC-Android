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
import godinner.lab.com.godinner.model.Produto;

public class PromocoesAdapter extends RecyclerView.Adapter<PromocoesAdapter.PromocoesViewholder> {

    private List<Produto> mProdutos;
    private Context context;
    private PromocaoOnClickListener mPromocaoOnClickListener;

    public PromocoesAdapter(List<Produto> mProdutos, Context context, PromocaoOnClickListener mPromocaoOnClickListener) {
        this.mProdutos = mProdutos;
        this.context = context;
        this.mPromocaoOnClickListener = mPromocaoOnClickListener;
    }

    @NonNull
    @Override
    public PromocoesViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.lista_promocoes, viewGroup, false);
        return new PromocoesViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocoesViewholder promocoesViewholder, final int i) {
        Produto p = mProdutos.get(i);
        promocoesViewholder.imgProduto.setImageDrawable(ContextCompat.getDrawable(context, R.color.colorWhite));
        promocoesViewholder.progress.setVisibility(View.VISIBLE);
        promocoesViewholder.txtProduto.setText(p.getNome());
        promocoesViewholder.txtPrecoAntigo.setText("7,00");
        promocoesViewholder.txtPrecoNovo.setText("5,00");

        promocoesViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPromocaoOnClickListener.onClickPromocao(v, i);
            }
        });

        try{
            URL urlImage = new URL("");

            CarregaImage carregaImage = new CarregaImage();
            carregaImage.mViewHolder = promocoesViewholder;
            carregaImage.execute(urlImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mProdutos != null ? mProdutos.size() : 0;
    }

    public interface PromocaoOnClickListener {
        void onClickPromocao(View view, int index);
    }

    protected class CarregaImage extends AsyncTask<URL, Void, Drawable> {

        private PromocoesViewholder mViewHolder;

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
            mViewHolder.imgProduto.setImageDrawable(drawable);
            mViewHolder.progress.setVisibility(View.INVISIBLE);
            super.onPostExecute(drawable);
        }
    }

    protected class PromocoesViewholder extends RecyclerView.ViewHolder {

        private ImageView imgProduto;
        private ProgressBar progress;
        private TextView txtProduto;
        private TextView txtPrecoAntigo;
        private TextView txtPrecoNovo;

        private PromocoesViewholder(@NonNull View itemView) {
            super(itemView);

            imgProduto = itemView.findViewById(R.id.image_produto);
            progress = itemView.findViewById(R.id.progressImage);
            txtProduto = itemView.findViewById(R.id.nome_produto);
            txtPrecoAntigo = itemView.findViewById(R.id.preco_antigo);
            txtPrecoNovo = itemView.findViewById(R.id.preco_novo);
        }
    }
}
