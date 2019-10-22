package godinner.lab.com.godinner;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import godinner.lab.com.godinner.adapter.ListaProdutosAdapter;
import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.PedidoDAO;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.ProdutoPedido;
import godinner.lab.com.godinner.model.SacolaPedido;

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

    public SacolaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        Toast.makeText(getContext(), "ABRINDO A SACOLA", Toast.LENGTH_SHORT).show();
        super.onResume();
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

        PedidoDAO mPedidoDAO = new PedidoDAO(view.getContext());
        ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(view.getContext());
        Consumidor c = mConsumidorDAO.consultarConsumidor();
        SacolaPedido mSacolaPedido = mPedidoDAO.consultarSacola();
        List<ProdutoPedido> listaProdutos = mPedidoDAO.getItensSacola();

        Double precoPedido = 0.0;
        Double valorEntrega = 0.0;

        if (mSacolaPedido.getValorEntrega() != 0.0)
            valorEntrega = mSacolaPedido.getValorEntrega();

        for (ProdutoPedido p : listaProdutos) {
            precoPedido += p.getPreco() * p.getQuantidade();
        }

        txtEntregarEm.setText(c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero());
        txtValorPedido.setText("R$ " + precoPedido.toString().replace(".", ","));
        txtValorEntrega.setText("Valor da Entrega: R$ " + valorEntrega.toString().replace(".", ","));
        txtTempoEntrega.setText("Tempo: 10min - 15min");
        txtNomeRestaurante.setText(mSacolaPedido.getNomeRestaurante());
        txtValorEntrega2.setText("R$ " + valorEntrega.toString().replace(".", ","));
        txtTotalGeral.setText("R$ " + String.valueOf(precoPedido + valorEntrega).replace(".", ","));

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
