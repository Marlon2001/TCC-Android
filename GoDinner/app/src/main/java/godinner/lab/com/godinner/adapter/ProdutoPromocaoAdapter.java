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
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import godinner.lab.com.godinner.R;
import godinner.lab.com.godinner.model.Produto;

public class ProdutoPromocaoAdapter extends RecyclerView.Adapter<ProdutoPromocaoAdapter.ProdutoViewHolder> {

    private List<Produto> mProdutos;
    private Context context;
    private ProdutoOnClickListener mProdutoOnClickListener;

    public ProdutoPromocaoAdapter(List<Produto> mProdutos, Context context, ProdutoOnClickListener mProdutoOnClickListener) {
        this.mProdutos = mProdutos;
        this.context = context;
        this.mProdutoOnClickListener = mProdutoOnClickListener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.lista_promocoes, viewGroup, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder produtoViewHolder, int i) {
        Produto p = mProdutos.get(i);
        produtoViewHolder.nomeProdto.setText(p.getNome());

        DecimalFormat f = new DecimalFormat("0.00");
        produtoViewHolder.precoAntigo.setText("R$ "+f.format(p.getPreco()));

        Double precoNovo = p.getPreco() * (p.getDesconto()/100);
        produtoViewHolder.precoNovo.setText("R$ "+f.format(precoNovo));

        produtoViewHolder.progressBar.setVisibility(View.VISIBLE);
        produtoViewHolder.imageProduto.setImageDrawable(ContextCompat.getDrawable(context, R.color.colorWhite));
    }

    @Override
    public int getItemCount() {
        return mProdutos != null ? mProdutos.size() : 0;
    }

    public interface ProdutoOnClickListener {
        void onClickProduto(View view, int index);
    }

    protected class CarregaImage extends AsyncTask<URL, Void, Drawable> {
        private ProdutoPromocaoAdapter.ProdutoViewHolder mViewHolder;

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
            mViewHolder.imageProduto.setImageDrawable(drawable);
            mViewHolder.progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(drawable);
        }
    }

    protected class ProdutoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageProduto;
        private ProgressBar progressBar;
        private TextView nomeProdto;
        private TextView precoAntigo;
        private TextView precoNovo;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProduto = itemView.findViewById(R.id.image_produto);
            progressBar = itemView.findViewById(R.id.progressImage);
            nomeProdto = itemView.findViewById(R.id.nome_produto);
            precoAntigo = itemView.findViewById(R.id.preco_antigo);
            precoNovo = itemView.findViewById(R.id.preco_novo);
        }
    }
}
