package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TokenUsuarioDAO extends SQLiteOpenHelper {

    public TokenUsuarioDAO(Context context) {
        super(context, "db_godinner", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_token(" +
                "id_token INTEGER PRIMARY KEY," +
                "token TEXT NOT NULL," +
                "validity TEXT NOT NULL)";
        db.execSQL(sql);

        ContentValues dados = new ContentValues();
        dados.put("token", "");
        dados.put("validity", "atual");

        db.insert("tbl_token", null, dados);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS tbl_token";
        db.execSQL(sql);
        onCreate(db);
    }

    public void salvarToken(String token){
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("id_token", 1);
        dados.put("token", token);
        dados.put("validity", "atual");

        String[] params = {"atual"};
        dbWrite.update("tbl_token", dados, "validity = ?", params);
    }

    public String consultarToken(){
        SQLiteDatabase dbRead = getReadableDatabase();
        String sql = "SELECT token FROM tbl_token WHERE validity = 'atual'";

        Cursor c = dbRead.rawQuery(sql, null);
        c.moveToNext();
        String token = c.getString(c.getColumnIndex("token"));

        return  token;

    }
}
