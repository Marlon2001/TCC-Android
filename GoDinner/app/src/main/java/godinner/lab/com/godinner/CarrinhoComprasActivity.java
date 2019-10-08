package godinner.lab.com.godinner;

import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class CarrinhoComprasActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_compras);

        txtEntregarEm = findViewById(R.id.txt_endereco_entrega);
        txtValorEntrega = findViewById(R.id.txt_valor_entrega);
        txtTempoEntrega = findViewById(R.id.txt_tempo_entrega);
        txtNomeRestaurante = findViewById(R.id.txt_nome_restaurante);
        mListaPedidos = findViewById(R.id.lista_pedidos);
        txtValorPedido = findViewById(R.id.txt_valor_pedido);
        txtValorEntrega2 = findViewById(R.id.txt_valor_entrega2);
        txtTotalGeral = findViewById(R.id.txt_total_geral);
        btnEsvaziarSacola = findViewById(R.id.btn_esvaziar_sacola);
        btnFinalizarCompra = findViewById(R.id.btn_finalizar_compra);


    }
}
