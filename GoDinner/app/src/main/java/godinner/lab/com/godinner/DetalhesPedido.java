package godinner.lab.com.godinner;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import godinner.lab.com.godinner.dao.PedidoDAO;
import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;
import godinner.lab.com.godinner.model.Restaurante;
import godinner.lab.com.godinner.model.RestauranteExibicao;
import godinner.lab.com.godinner.model.SacolaPedido;

public class DetalhesPedido extends AppCompatActivity implements View.OnClickListener {

    private Produto mProduto;
    private RestauranteExibicao mRestauranteExibicao;

    private ImageView imageProduto;
    private TextView txtNomeProduto;
    private TextView txtDetalhesDoProduto;
    private Button btnValorTotal;
    private Button btnSomar;
    private Button btnSubtrair;
    private Button btnTotal;

    private CollapsingToolbarLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_do_pedido);

        imageProduto = findViewById(R.id.image_produto_detalhes);
        txtNomeProduto = findViewById(R.id.text_nome_produto);
        txtDetalhesDoProduto = findViewById(R.id.text_descricao_produto);
        btnSomar = findViewById(R.id.btn_mais_um);
        btnSubtrair = findViewById(R.id.btn_menos_um);
        btnTotal = findViewById(R.id.btn_total_produtos);
        btnValorTotal = findViewById(R.id.valor_total);
        toolbar = findViewById(R.id.collapseToolbar);

        btnSomar.setOnClickListener(this);
        btnSubtrair.setOnClickListener(this);
        btnTotal.setOnClickListener(this);
        btnValorTotal.setOnClickListener(this);

        Intent intent = getIntent();
        mProduto = (Produto) intent.getSerializableExtra("produto_clicado");
        mRestauranteExibicao = (RestauranteExibicao) intent.getSerializableExtra("restaurante");

        String url = MainActivity.ipServidorFotos + "/" + (mProduto.getFotos().size() == 0 ? MainActivity.fotoLanchePadrao : mProduto.getFotos().get(0).getFoto());
        Picasso.get()
                .load(url)
                .resize(150, 200)
                .into(imageProduto);

        PedidoDAO dao = new PedidoDAO(this);

        SacolaPedido mSacolaPedido = new SacolaPedido();

        mSacolaPedido.setIdRestaurante(0);
        mSacolaPedido.setNomeRestaurante("");
        mSacolaPedido.setValorEntrega(0.0);
        mSacolaPedido.setValorTotalPedido(0.0);

        if(dao.verificarSacola()){
            dao.criarSacola(mSacolaPedido);
        }

        txtNomeProduto.setText(mProduto.getNome());
        txtDetalhesDoProduto.setText(mProduto.getDescricao());
        btnValorTotal.setText("R$ " + mProduto.getPreco().toString());

        toolbar.setTitle(mProduto.getNome());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mais_um:
                int total = Integer.parseInt(btnTotal.getText().toString()) + 1;
                btnTotal.setText(total+"");

                PedidoDAO dao = new PedidoDAO(this);

                if(dao.sacolaIsNull()){
                    SacolaPedido s = new SacolaPedido();

                    s.setIdRestaurante(mRestauranteExibicao.getId());
                    s.setNomeRestaurante(mRestauranteExibicao.getRazaoSocial());
                    s.setValorEntrega(mRestauranteExibicao.getPrecoEntrega());
                    s.setValorTotalPedido(mRestauranteExibicao.getPrecoEntrega() + mProduto.getPreco() * total);

                    dao.atualizarSacola(s);
                }

                ProdutoPedido p = new ProdutoPedido();
                p.setId(mProduto.getId());
                p.setNome(mProduto.getNome());
                p.setPreco(mProduto.getPreco());
                p.setQuantidade(total);

                dao.salvarProduto(p);
                break;
            case R.id.btn_menos_um:
                Toast.makeText(this, "Menos 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_total_produtos:
                Toast.makeText(this, "Valor total", Toast.LENGTH_SHORT).show();
                break;
            case R.id.valor_total:
                finish();
                break;
            default:
                break;
        }
    }
}
