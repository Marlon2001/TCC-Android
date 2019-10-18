package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;
import godinner.lab.com.godinner.model.SacolaPedido;

public class PedidoDAO extends SQLiteOpenHelper {

    public PedidoDAO(Context context) {
        super(context, "db_godinner_produto_pedido", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE IF NOT EXISTS tbl_produto(" +
                "id_produto INTEGER PRIMARY KEY," +
                "id_produto2 INTEGER, " +
                "nome TEXT NOT NULL," +
                "quantidade INTEGER NOT NULL," +
                "preco DOUBLE NOT NULL)";

        String sql2 = "CREATE TABLE IF NOT EXISTS tbl_sacola(" +
                "id_sacola INTEGER PRIMARY KEY," +
                "id_restaurante INTEGER NOT NULL," +
                "nome_restaurante TEXT NOT NULL," +
                "valor_entrega DOUBLE NOT NULL," +
                "valor_total_pedido DOUBLE NOT NULL)";

        String sql3 = "CREATE TABLE IF NOT EXISTS tbl_produto_sacola(" +
                "id_produto_sacola INTEGER PRIMARY KEY," +
                "id_produto INTEGER  REFERENCES tbl_produto(id_produto)ON UPDATE CASCADE," +
                "id_sacola INTEGER  REFERENCES tbl_sacola(id_sacola) ON UPDATE CASCADE)";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public int getIdSacolaAtiva() {
        SQLiteDatabase dbRead = getReadableDatabase();

        String sql = "SELECT id_sacola FROM tbl_sacola order by id_sacola desc limit 1";
        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        int id = c.getInt(c.getColumnIndex("id_sacola"));
        c.close();

        return id;
    }

    public void salvarProdutoPedido(ProdutoPedido p) {
        int id = getIdSacolaAtiva();

        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosProdutoSacola = new ContentValues();
        dadosProdutoSacola.put("id_produto", p.getId());
        dadosProdutoSacola.put("id_sacola", id);

        dbWrite.insert("tbl_produto_sacola", null, dadosProdutoSacola);
    }

    public void salvarProduto(ProdutoPedido p) {
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosProduto = new ContentValues();

        if (p.getQuantidade() == 1) {
            dadosProduto.put("id_produto2", p.getId());
            dadosProduto.put("nome", p.getNome());
            dadosProduto.put("preco", p.getPreco());
            dadosProduto.put("quantidade", p.getQuantidade());
            dbWrite.insert("tbl_produto", null, dadosProduto);

            ProdutoPedido pedido = new ProdutoPedido();
            pedido.setId(p.getId());

            salvarProdutoPedido(pedido);
        } else {
            String[] args = {p.getId().toString()};

            dadosProduto.put("quantidade", p.getQuantidade());
            dbWrite.update("tbl_produto", dadosProduto, "id_produto2 = ?", args);
        }
    }

    public boolean verificarSacola() {
        SQLiteDatabase dbRead = getReadableDatabase();

        String sql = "SELECT COUNT(*) AS total FROM tbl_sacola";
        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        int total = c.getInt(c.getColumnIndex("total"));
        c.close();

        if (total == 0) {
            return true;
        }

        return false;
    }

    public boolean sacolaIsNull() {
        SQLiteDatabase dbRead = getReadableDatabase();

        String sql = "SELECT COUNT(*) AS total FROM tbl_sacola WHERE id_restaurante = 0.0";
        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        int total = c.getInt(c.getColumnIndex("total"));
        c.close();

        if (total == 1) {
            return true;
        }

        return false;
    }

    public void atualizarSacola(SacolaPedido s) {
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosSacola = new ContentValues();
        dadosSacola.put("id_restaurante", s.getIdRestaurante());
        dadosSacola.put("nome_restaurante", s.getNomeRestaurante());
        dadosSacola.put("valor_entrega", s.getValorEntrega());
        dadosSacola.put("valor_total_pedido", s.getValorTotalPedido());

        String[] args = {"1"};
        dbWrite.update("tbl_sacola", dadosSacola, "id_sacola = ?", args);
    }

    public void criarSacola(SacolaPedido s) {
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosSacola = new ContentValues();
        dadosSacola.put("id_restaurante", s.getIdRestaurante());
        dadosSacola.put("nome_restaurante", s.getNomeRestaurante());
        dadosSacola.put("valor_entrega", s.getValorEntrega());
        dadosSacola.put("valor_total_pedido", s.getValorTotalPedido());

        dbWrite.insert("tbl_sacola", null, dadosSacola);
    }

    public SacolaPedido consultarSacola(){
        SQLiteDatabase dbRead = getReadableDatabase();

        SacolaPedido s = new SacolaPedido();

        String sql = "SELECT * FROM tbl_sacola WHERE id_sacola = 1";
        Cursor c = dbRead.rawQuery(sql, null);

        if(c.moveToNext()) {
            s.setIdSacola(c.getInt(c.getColumnIndex("id_sacola")));
            s.setIdRestaurante(c.getInt(c.getColumnIndex("id_restaurante")));
            s.setNomeRestaurante(c.getString(c.getColumnIndex("nome_restaurante")));
            s.setValorEntrega(c.getDouble(c.getColumnIndex("valor_entrega")));
            s.setValorTotalPedido(c.getDouble(c.getColumnIndex("valor_total_pedido")));

            c.close();
        }

        return s;
    }

    public List<ProdutoPedido> getItensSacola() {
        SQLiteDatabase dbRead = getReadableDatabase();
        List<ProdutoPedido> listProdutos = new ArrayList<>();

        String sql = "SELECT * FROM tbl_produto";

        Cursor c = dbRead.rawQuery(sql, null);
        while (c.moveToNext()) {
            ProdutoPedido p = new ProdutoPedido();
            p.setId(c.getInt(c.getColumnIndex("id_produto")));
            p.setNome(c.getString(c.getColumnIndex("nome")));
            p.setQuantidade(c.getInt(c.getColumnIndex("quantidade")));
            p.setPreco(c.getDouble(c.getColumnIndex("preco")));
//            p.setTotal(c.getDouble(c.getColumnIndex("valor_total_pedido")));
//            p.setDesconto(c.getDouble(c.getColumnIndex("valor_entrega")));
            listProdutos.add(p);
        }

        return listProdutos;
    }

    private int getLastId() {
        SQLiteDatabase dbRead = getReadableDatabase();

        String sql = "SELECT id_produto FROM tbl_produto ORDER BY id_produto DESC LIMIT 1";
        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        int idProduto = c.getInt(c.getColumnIndex("id_produto"));

        return idProduto;
    }
}