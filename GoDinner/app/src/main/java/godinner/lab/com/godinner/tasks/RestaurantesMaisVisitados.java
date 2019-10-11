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

import godinner.lab.com.godinner.HomeFragment;
import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.TelaInicialActivity;
import godinner.lab.com.godinner.model.RestauranteExibicao;

public class RestaurantesMaisVisitados extends AsyncTask {

    private ArrayList<RestauranteExibicao> restaurantes;
    private Integer idConsumidor;
    private String token;

    public RestaurantesMaisVisitados(int idConsumidor, String token) {
        this.idConsumidor = idConsumidor;
        this.token = token;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL(MainActivity.ipServidor+"/restaurante/exibicao/"+idConsumidor);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("token", token);
            conexao.setDoInput(true);

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
            RestauranteExibicao restaurante;

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject mObject = (JSONObject) jsonArray.get(i);
                restaurante = new RestauranteExibicao();

                restaurante.setId(mObject.getInt("id"));
                restaurante.setRazaoSocial(mObject.getString("razaoSocial"));
                restaurante.setTelefone(mObject.getString("telefone"));
                restaurante.setPrecoEntrega(mObject.getDouble("valorEntrega"));
                restaurante.setFoto(mObject.getString("foto"));
                restaurante.setDistancia(mObject.getString("distancia"));
                restaurante.setTempoEntrega(mObject.getString("tempoEntrega"));
                restaurante.setNota(mObject.getString("nota"));
                restaurantes.add(restaurante);
            }

            HomeFragment.restaurantesMaisVisitados = restaurantes;
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