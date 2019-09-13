package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
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
                "token TEXT NOT NULL)";
        db.execSQL(sql);
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
        dados.put("token", token);
        dbWrite.insert("tbl_token", null, dados);
    }
}
