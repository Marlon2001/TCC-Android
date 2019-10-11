package godinner.lab.com.godinner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import godinner.lab.com.godinner.MainActivity;
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

        promocoesViewholder.txtProduto.setText(p.getNome());
        promocoesViewholder.txtPrecoAntigo.setText(Html.fromHtml("<del>R$ "+p.getPreco()+"</del>"));

        Double desconto = p.getPreco() * (p.getDesconto()/100);
        promocoesViewholder.txtPrecoNovo.setText((p.getPreco()-desconto)+"");

        promocoesViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPromocaoOnClickListener.onClickPromocao(v, i);
            }
        });


        String url = MainActivity.ipServidorFotos +"/"+(p.getFotos().size() == 0 ? MainActivity.fotoLanchePadrao:  p.getFotos().get(0).getFoto());
        Picasso.get()
                .load(url)
                .resize(120, 120)
                .into(promocoesViewholder.imgProduto);
    }

    @Override
    public int getItemCount() {
        return mProdutos != null ? mProdutos.size() : 0;
    }

    public interface PromocaoOnClickListener {
        void onClickPromocao(View view, int index);
    }

    protected class PromocoesViewholder extends RecyclerView.ViewHolder {

        private ImageView imgProduto;
        private TextView txtProduto;
        private TextView txtPrecoAntigo;
        private TextView txtPrecoNovo;

        private PromocoesViewholder(@NonNull View itemView) {
            super(itemView);

            imgProduto = itemView.findViewById(R.id.image_produto);
            txtProduto = itemView.findViewById(R.id.nome_produto);
            txtPrecoAntigo = itemView.findViewById(R.id.preco_antigo);
            txtPrecoNovo = itemView.findViewById(R.id.preco_novo);
        }
    }
}
