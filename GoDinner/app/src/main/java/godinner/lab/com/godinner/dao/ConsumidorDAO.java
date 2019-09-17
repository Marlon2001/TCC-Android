package godinner.lab.com.godinner.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.Consumer;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.model.Consumidor;

public class ConsumidorDAO extends SQLiteOpenHelper {

        public ConsumidorDAO(Context context) {
            super(context, "db_godinner", null, 3);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS tbl_consumidor(" +
                    "id_consumidor INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "foto_perfil NOT NULL,"+
                    "id_endereco INTEGER NOT NULL)";
            db.execSQL(sql);

            ContentValues dados = new ContentValues();
            dados.put("id_consumidor", 1);
            dados.put("nome", "");
            dados.put("email", "");
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
            this.onUpgrade(dbWrite, 3, 3);

            ContentValues dados = new ContentValues();
            dados.put("id_consumidor", c.getId());
            dados.put("nome", c.getNome());
            dados.put("email", c.getEmail());
            dados.put("id_endereco", c.getIdEndereco());
            dados.put("foto_perfil", c.getFotoPerfil());

            String[] params = {"0"};

            dbWrite.update("tbl_consumidor", dados,  "id_consumidor > ?", new String[] {"0"});
//            db.update("messages", cv, "id = ?",  });
        }

        public Consumidor consultarConsumidor(){
            Consumidor co = new Consumidor();
            SQLiteDatabase dbRead = getReadableDatabase();
            String sql = "SELECT * FROM tbl_consumidor LIMIT 1";

            Cursor c = dbRead.rawQuery(sql, null);
            if(c.moveToNext()){


                co.setId(c.getInt(c.getColumnIndex("id_consumidor")));
                co.setNome(c.getString(c.getColumnIndex("nome")));
                co.setEmail(c.getString(c.getColumnIndex("email")));
                co.setFotoPerfil(c.getString(c.getColumnIndex("foto_perfil")));
                co.setIdEndereco(c.getInt(c.getColumnIndex("id_endereco")));
            }else{

            }



            return  co;

        }
}


