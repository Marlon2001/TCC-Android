package godinner.lab.com.godinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import godinner.lab.com.godinner.dao.PedidoDAO;
import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;
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
        Picasso.get().load(url).resize(150, 200).into(imageProduto);

        PedidoDAO mPedidoDAO = new PedidoDAO(this);
        ProdutoPedido p = mPedidoDAO.consultarProdutoById(mProduto.getId());

        if (p.getQuantidade() != 0)
            btnTotal.setText(p.getQuantidade() + "");

        txtNomeProduto.setText(mProduto.getNome());
        txtDetalhesDoProduto.setText(mProduto.getDescricao());
        btnValorTotal.setText("R$ " + mProduto.getPreco().toString());

        toolbar.setTitle(mProduto.getNome());
    }

    @Override
    public void onClick(View v) {
        int total = Integer.parseInt(btnTotal.getText().toString());
        PedidoDAO mPedidoDAO = new PedidoDAO(this);
        ProdutoPedido p = new ProdutoPedido();

        switch (v.getId()) {
            case R.id.btn_mais_um:
                total++;
                btnTotal.setText(String.valueOf(total));

                if (mPedidoDAO.sacolaIsNull()) {
                    SacolaPedido s = new SacolaPedido();

                    s.setIdRestaurante(mRestauranteExibicao.getId());
                    s.setNomeRestaurante(mRestauranteExibicao.getRazaoSocial());
                    s.setTempoEntrega(mRestauranteExibicao.getTempoEntrega());
                    s.setValorEntrega(mRestauranteExibicao.getPrecoEntrega());
                    s.setValorTotalPedido(mRestauranteExibicao.getPrecoEntrega() + mProduto.getPreco() * total);

                    mPedidoDAO.atualizarSacola(s);
                }

                p.setId(mProduto.getId());
                p.setNome(mProduto.getNome());
                p.setPreco(mProduto.getPreco());
                p.setQuantidade(total);

                if(p.getQuantidade() == 1)
                    mPedidoDAO.salvarProduto(p, "novo");
                else
                    mPedidoDAO.salvarProduto(p, "editar");
                break;
            case R.id.btn_menos_um:
                if (total > 0) {
                    total--;
                    btnTotal.setText(String.valueOf(total));

                    p.setId(mProduto.getId());
                    p.setNome(mProduto.getNome());
                    p.setPreco(mProduto.getPreco());
                    p.setQuantidade(total);

                    if (p.getQuantidade() > 0)
                        mPedidoDAO.salvarProduto(p, "editar");
                    else if (p.getQuantidade() == 0)
                        mPedidoDAO.salvarProduto(p, "excluir");
                }
                break;
            case R.id.valor_total:
                finish();
                break;
            default:
                break;
        }
    }
}
