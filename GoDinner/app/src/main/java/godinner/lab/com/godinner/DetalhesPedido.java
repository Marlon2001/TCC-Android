package godinner.lab.com.godinner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import godinner.lab.com.godinner.model.Produto;

public class DetalhesPedido extends AppCompatActivity {
    private Produto mProduto;
    private ImageView imageProduto;
    private TextView descricaoProduto;
    private Button btnValorTotal;
    private Button somar1;
    private Button subitrair1;
    private Button btnTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_do_pedido);
        imageProduto = findViewById(R.id.image_produto);
        descricaoProduto = findViewById(R.id.descricao);
        btnValorTotal = findViewById(R.id.valor_total);
        somar1 = findViewById(R.id.btn_mais_um);
        subitrair1 = findViewById(R.id.btn_menos_um);
        btnTotal = findViewById(R.id.btn_total_produtos);

        Intent intent = getIntent();
        mProduto = (Produto) intent.getSerializableExtra("produto_clicado");

        String urlString = MainActivity.ipServidor + "/" + mProduto.getFotos().get(0).getFoto();
        InputStream content;

        try {
            URL url = new URL(urlString);
            content = (InputStream) url.getContent();
            imageProduto.setImageDrawable(Drawable.createFromStream(content, "src"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        descricaoProduto.setText(mProduto.getDescricao());
        btnValorTotal.setText("R$ "+mProduto.getPreco().toString());
        btnTotal.setText("1");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        
    }


}
