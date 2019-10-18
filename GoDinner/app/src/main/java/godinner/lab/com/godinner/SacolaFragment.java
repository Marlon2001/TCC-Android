package godinner.lab.com.godinner;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import godinner.lab.com.godinner.adapter.ListaProdutosAdapter;
import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.PedidoDAO;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;

public class SacolaFragment extends Fragment {

    private TextView txtEntregarEm;   // Jardim Vila nova, 54
    private TextView txtValorEntrega; // Valor da Entrega: R$ 10,00
    private TextView txtTempoEntrega; // Entrega: 10 - 15min
    private TextView txtNomeRestaurante;

    private ListView mListaPedidos;

    private TextView txtValorPedido;    // R$ 10,00
    private TextView txtValorEntrega2;  // R$ 30,00
    private TextView txtTotalGeral;     // R$ 40,00

    private MaterialButton btnEsvaziarSacola;
    private MaterialButton btnFinalizarCompra;

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public SacolaFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sacola, container, false);

        txtEntregarEm = view.findViewById(R.id.txt_endereco_entrega);
        txtValorEntrega = view.findViewById(R.id.txt_valor_entrega);
        txtTempoEntrega = view.findViewById(R.id.txt_tempo_entrega);
        txtNomeRestaurante = view.findViewById(R.id.txt_nome_restaurante);

        mListaPedidos = view.findViewById(R.id.lista_pedidos);

        txtValorPedido = view.findViewById(R.id.txt_valor_pedido);
        txtValorEntrega2 = view.findViewById(R.id.txt_valor_entrega2);
        txtTotalGeral = view.findViewById(R.id.txt_total_geral);

        btnEsvaziarSacola = view.findViewById(R.id.btn_esvaziar_sacola);
        btnFinalizarCompra = view.findViewById(R.id.btn_finalizar_compra);

        PedidoDAO dao = new PedidoDAO(view.getContext());
        ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(view.getContext());
        Consumidor c = mConsumidorDAO.consultarConsumidor();
        List<ProdutoPedido> listaProdutos = dao.getItensSacola();

        Double precoPedido = 0.0;
        for(ProdutoPedido p : listaProdutos){
            precoPedido += p.getPreco() * p.getQuantidade();
        }

        txtEntregarEm.setText(c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero());
        txtValorPedido.setText("R$ " + precoPedido.toString().replace(".", ","));


        ListaProdutosAdapter mAdapter = new ListaProdutosAdapter(listaProdutos, view.getContext());
        mListaPedidos.setAdapter(mAdapter);

        return view;
    }

    public static SacolaFragment newInstance(String param1, String param2) {
        SacolaFragment fragment = new SacolaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
}
