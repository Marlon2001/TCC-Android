package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Endereco;

public class ConsumidorDAO extends SQLiteOpenHelper {

    public ConsumidorDAO(Context context) {
        super(context, "db_godinner2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_consumidor(" +
                "id_consumidor INTEGER NOT NULL," +
                "id_servidor INTEGER NOT NULL," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "cpf TEXT NOT NULL," +
                "telefone TEXT NOT NULL," +
                "foto_perfil NOT NULL,"+
                "id_endereco INTEGER NOT NULL)";
        db.execSQL(sql);

        ContentValues dados = new ContentValues();
        dados.put("id_consumidor", 1);
        dados.put("id_servidor", 0);
        dados.put("nome", "");
        dados.put("email", "");
        dados.put("cpf", "");
        dados.put("telefone", "");
        dados.put("foto_perfil", "");
        dados.put("id_endereco", 0);
        db.insert("tbl_consumidor", null, dados);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS tbl_consumidor";
        db.execSQL(sql);
        onCreate(db);
    }

    public void salvarConsumidorLogado(Consumidor c){
        SQLiteDatabase dbWrite = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("id_servidor", c.getIdConsumidor());
        dados.put("nome", c.getNome());
        dados.put("email", c.getEmail());
        dados.put("cpf", c.getCpf());
        dados.put("telefone", c.getTelefone());
        dados.put("foto_perfil", c.getFotoPerfil());
        dados.put("id_endereco", c.getEndereco().getIdEndereco());

        String[] params = {"1"};
        dbWrite.update("tbl_consumidor", dados,  "id_consumidor = ?", params);
    }

    public Consumidor consultarConsumidor(){
        Consumidor c = new Consumidor();
        SQLiteDatabase dbRead = getReadableDatabase();
        String sql = "SELECT * FROM tbl_consumidor WHERE id_consumidor = 1";

        Cursor c1 = dbRead.rawQuery(sql, null);
        if(c1.moveToNext()){
            c.setIdServidor(c1.getInt(c1.getColumnIndex("id_servidor")));
            c.setIdConsumidor(c1.getInt(c1.getColumnIndex("id_consumidor")));
            c.setNome(c1.getString(c1.getColumnIndex("nome")));
            c.setEmail(c1.getString(c1.getColumnIndex("email")));
            c.setCpf(c1.getString(c1.getColumnIndex("cpf")));
            c.setTelefone(c1.getString(c1.getColumnIndex("telefone")));
            c.setFotoPerfil(c1.getString(c1.getColumnIndex("foto_perfil")));

            Endereco e = new Endereco();
            e.setIdEndereco(c1.getInt(c1.getColumnIndex("id_endereco")));
            c.setEndereco(e);
        }
        c1.close();
        return c;
    }
}