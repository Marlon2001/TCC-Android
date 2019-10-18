package godinner.lab.com.godinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import godinner.lab.com.godinner.R;
import godinner.lab.com.godinner.model.ProdutoPedido;

public class ListaProdutosAdapter extends BaseAdapter {

    private List<ProdutoPedido> pedidos;
    private Context context;

    public ListaProdutosAdapter(List<ProdutoPedido> pedidos, Context context) {
        this.pedidos = pedidos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProdutoPedido pedido = this.pedidos.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lista_pedidos, null);

        TextView txtNomePedido = view.findViewById(R.id.nome_pedido);
        TextView txtPrecoPedido = view.findViewById(R.id.preco_pedido);
        ImageView mOpcoesPedido = view.findViewById(R.id.opcoes_pedido);

        txtNomePedido.setText(pedido.getQuantidade() + "x " + pedido.getNome());
        txtPrecoPedido.setText("R$ " + pedido.getPreco() * pedido.getQuantidade());

        mOpcoesPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
