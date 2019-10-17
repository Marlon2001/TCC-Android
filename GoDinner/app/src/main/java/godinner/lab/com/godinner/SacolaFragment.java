package godinner.lab.com.godinner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import godinner.lab.com.godinner.dao.PedidoDAO;
import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;

public class SacolaFragment extends Fragment {

    private TextView txtSacola;

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
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        txtSacola = view.findViewById(R.id.txt_sacola);

        PedidoDAO dao = new PedidoDAO(view.getContext());

        for(ProdutoPedido p : dao.getItensSacola()){
            Log.d("Produto"+p.getId(), "Nome: " + p.getNome() + " Quantidade: " + p.getQuantidade() + " Preço: " + p.getPreco() + "\n");
        }
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
