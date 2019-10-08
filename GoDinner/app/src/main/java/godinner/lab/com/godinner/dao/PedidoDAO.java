package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String sql3 = "CREATE TABLE IF NOT EXISTS tbl_produtos_sacola(" +
                "id_sacola INTEGER PRIMARY KEY," +
                "id_produto INTEGER  REFERENCES tbl_produto(id_produto)ON UPDATE CASCADE," +
                "id_sacola INTEGER  REFERENCES tbl_sacola(id_sacola) ON UPDATE CASCADE)";
        db.execSQL(sql);db.execSQL(sql2);db.execSQL(sql3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
