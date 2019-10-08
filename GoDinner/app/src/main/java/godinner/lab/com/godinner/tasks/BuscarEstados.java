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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.SplashActivity;
import godinner.lab.com.godinner.model.Estado;

public class BuscarEstados extends AsyncTask {

    private ArrayList<Estado> estados;

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL( MainActivity.ipServidor+"/estado");

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
            estados = new ArrayList<>();
            Estado estado;

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject mObject = (JSONObject) jsonArray.get(i);
                estado = new Estado();
                estado.setIdEstado(mObject.getInt("id"));
                estado.setEstado(mObject.getString("estado"));
                estado.setUf(mObject.getString("uf"));
                estados.add(estado);
            }

            SplashActivity.estados = estados;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
