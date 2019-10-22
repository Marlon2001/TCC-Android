package godinner.lab.com.godinner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

    private Context context;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sacola, container, false);
        context = getContext();

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

        btnEsvaziarSacola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedidoDAO mPedidoDAO = new PedidoDAO(context);

                mPedidoDAO.esvaziarSacola();
                mPedidoDAO.close();
                onResume();
            }
        });

        return view;
    }

    public static SacolaFragment newInstance(String param1, String param2) {
        SacolaFragment fragment = new SacolaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void mAtualizarSacola() {
        PedidoDAO mPedidoDAO = new PedidoDAO(context);
        ConsumidorDAO mConsumidorDAO = new ConsumidorDAO(context);
        Consumidor c = mConsumidorDAO.consultarConsumidor();
        SacolaPedido mSacolaPedido = mPedidoDAO.consultarSacola();
        List<ProdutoPedido> listaProdutos = mPedidoDAO.getItensSacola();

        Double precoPedido = 0.0;
        Double valorEntrega = 0.0;
        String tempoEntrega = "0 min";
        String nomeRestaurante = "Nenhum Pedido";

        if (mSacolaPedido.getValorEntrega() != 0.0)
            valorEntrega = mSacolaPedido.getValorEntrega();

        if(!mSacolaPedido.getTempoEntrega().isEmpty())
            tempoEntrega = mSacolaPedido.getTempoEntrega();

        if(!mSacolaPedido.getNomeRestaurante().isEmpty())
            nomeRestaurante = mSacolaPedido.getNomeRestaurante();

        for (ProdutoPedido p : listaProdutos) {
            precoPedido += p.getPreco() * p.getQuantidade();
        }

        txtEntregarEm.setText(c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero());
        txtValorPedido.setText("R$ " + precoPedido.toString().replace(".", ","));
        txtValorEntrega.setText("Valor da Entrega: R$ " + valorEntrega.toString().replace(".", ","));
        txtTempoEntrega.setText("Tempo: " + tempoEntrega);
        txtNomeRestaurante.setText(nomeRestaurante);
        txtValorEntrega2.setText("R$ " + valorEntrega.toString().replace(".", ","));
        txtTotalGeral.setText("R$ " + String.valueOf(precoPedido + valorEntrega).replace(".", ","));

        ListaProdutosAdapter mAdapter = new ListaProdutosAdapter(listaProdutos, context);
        mListaPedidos.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAtualizarSacola();
    }
}
