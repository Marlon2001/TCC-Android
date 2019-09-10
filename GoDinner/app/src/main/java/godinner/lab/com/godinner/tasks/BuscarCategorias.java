package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.TelaInicialActivity;
import godinner.lab.com.godinner.model.Categoria;

public class BuscarCategorias extends AsyncTask{

    private ArrayList<Categoria> categorias;
    
    @Override
    protected Object doInBackground(Object[] o) {
        try{
            URL url = new URL("http://"+ MainActivity.ipServidor+"/categorias/todas");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";
            String dados = "";

            while (linha != null){
                linha = bufferedReader.readLine();
                dados = dados + linha;
            }

            JSONArray jsonArray = new JSONArray(dados);
            categorias = new ArrayList<>();
            Categoria categoria;

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject mObject = (JSONObject) jsonArray.get(i);
                categoria = new Categoria();
                categoria.setUrlImage(mObject.getString("foto"));
                categoria.setNome(mObject.getString("nome"));
                categorias.add(categoria);
            }

            TelaInicialActivity.categorias = categorias;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
