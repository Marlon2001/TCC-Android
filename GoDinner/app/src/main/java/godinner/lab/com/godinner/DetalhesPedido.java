package godinner.lab.com.godinner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.utils.Imagens;

public class DetalhesPedido extends AppCompatActivity implements View.OnClickListener{
    private Produto mProduto;
    private ImageView imageProduto;
    private TextView descricaoProduto, txtDetalhesDoProduto;
    private Button btnValorTotal;
    private Button somar1;
    private Button subitrair1;
    private Button btnTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_do_pedido);
        imageProduto = findViewById(R.id.image_produto_detalhes);
        descricaoProduto = findViewById(R.id.text_descricao_produto);
        btnValorTotal = findViewById(R.id.valor_total);
        somar1 = findViewById(R.id.btn_mais_um);
        subitrair1 = findViewById(R.id.btn_menos_um);
        btnTotal = findViewById(R.id.btn_total_produtos);
        txtDetalhesDoProduto = findViewById(R.id.text_descricao_produto);

        Intent intent = getIntent();
        mProduto = (Produto) intent.getSerializableExtra("produto_clicado");


        Imagens i = new Imagens();
        URL url ;
        try {
            url = new URL(MainActivity.ipServidor + "/" + mProduto.getFotos().get(0).getFoto());
            i.execute(url);
            imageProduto.setImageDrawable(i.drawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        descricaoProduto.setText(mProduto.getDescricao());
        btnValorTotal.setText("R$ "+mProduto.getPreco().toString());
        txtDetalhesDoProduto.setText(mProduto.getNome());
        btnTotal.setText("1");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mais_um:
                Toast.makeText(this, "Somar 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_menos_um:
                Toast.makeText(this, "Menos 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_total_produtos:
                finish();
                break;
            default:
                break;
        }

    }


}
