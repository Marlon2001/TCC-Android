package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import godinner.lab.com.godinner.model.Produto;
import godinner.lab.com.godinner.model.ProdutoPedido;
import godinner.lab.com.godinner.model.SacolaPedido;

public class PedidoDAO extends SQLiteOpenHelper {

public PedidoDAO(Context context) {
        super(context, "db_godinner_produto_pedido", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_produto(" +
                "id_produto INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "quantidade INTEGER NOT NULL," +
                "preco DOUBLE NOT NULL)";


        String sql2 = "CREATE TABLE IF NOT EXISTS tbl_sacola(" +
                "id_sacola INTEGER PRIMARY KEY," +
                "id_restaurante INTEGER NOT NULL," +
                "valor_entrega DOUBLE NOT NULL," +
                "valor_total_pedido TEXT not null)";

        String sql3 = "CREATE TABLE IF NOT EXISTS tbl_produto_sacola(" +
                "id_produto_sacola INTEGER PRIMARY KEY," +
                "id_produto INTEGER  REFERENCES tbl_produto(id_produto)ON UPDATE CASCADE," +
                "id_sacola INTEGER  REFERENCES tbl_sacola(id_sacola) ON UPDATE CASCADE)";
        db.execSQL(sql);db.execSQL(sql2);db.execSQL(sql3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getIdSacolaAtiva(){
        SQLiteDatabase dbRead = getReadableDatabase();
        String sql = "SELECT token FROM tbl_sacola order by id_sacola desc limit 1";
        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        int id = c.getInt(c.getColumnIndex("id_produto_sacola"));

        return  id;
    }
    public void salvar_produto_sacola(Produto p){
        int id = this.getIdSacolaAtiva();

        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosProdutoSacola = new ContentValues();
        dadosProdutoSacola.put("id_produto", p.getId());
        dadosProdutoSacola.put("id_sacola", id);

        dbWrite.insert("tbl_produto_sacola", null, dadosProdutoSacola);

    }

    public void salvarPedido(ProdutoPedido p){
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosProduto = new ContentValues();
        dadosProduto.put("id_produto", p.getId());
        dadosProduto.put("nome", p.getNome());
        dadosProduto.put("quantidade", p.getQuantidade());
        dadosProduto.put("preco", p.getPreco());

        dbWrite.insert("tbl_produto", null,dadosProduto);

    }

    public void salvarSacola(SacolaPedido s){
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dadosSacola = new ContentValues();
        dadosSacola.put("id_produto", s.getId());
        dadosSacola.put("id_restaurante", s.getRestaurante().getId());
        dadosSacola.put("valor_entrega", s.getValorEntrega());
        dadosSacola.put("valor_total_pedido", s.getValorTotalPedido());

        dbWrite.insert("tbl_sacola", null,dadosSacola);
    }

}
