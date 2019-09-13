package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.TelaInicialActivity;
import godinner.lab.com.godinner.model.Restaurante;

public class RestaurantesMaisVisitados extends AsyncTask {

    private ArrayList<Restaurante> restaurantes;

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONStringer jsonToken = new JSONStringer();
        try {
            jsonToken.object();
            jsonToken.key("token").value(1);
            jsonToken.endObject();

            URL url = new URL("http://"+MainActivity.ipServidor+"/restaurantes/proximo");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);

            PrintStream outputStream = new PrintStream(conexao.getOutputStream());
            outputStream.print(jsonToken);

            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";
            String dados = "";

            while (linha != null) {
                linha = bufferedReader.readLine();
                dados += linha;
            }

            JSONArray jsonArray = new JSONArray(dados);
            restaurantes = new ArrayList<>();
            Restaurante restaurante;

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject mObject = (JSONObject) jsonArray.get(i);
                restaurante = new Restaurante();
                restaurante.setNome(mObject.getString("nome"));
                restaurante.setUrlImage(mObject.getString("foto"));
                restaurantes.add(restaurante);
            }

            TelaInicialActivity.restaurantesProximos = restaurantes;
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
