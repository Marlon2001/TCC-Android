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
import godinner.lab.com.godinner.model.Restaurante;
import godinner.lab.com.godinner.model.RestauranteExibicao;

public class BuscarRestaurantesProximos extends AsyncTask {

    private ArrayList<RestauranteExibicao> restaurantes;
    private int idConsumidor;
    public BuscarRestaurantesProximos(int idConsumidor){
        this.idConsumidor = idConsumidor;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            URL url = new URL("http://"+ MainActivity.ipServidor+"/restaurante/todos/exibicao/"+idConsumidor);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhQGEuYSIsImV4cCI6MTU2ODc2MTQ4MSwiaWF0IjoxNTY4NzQzNDgxfQ.d9xM7e4RwOypK-KxlJBaEtVo_YDb94j4ngIkwa3gUvSxRIKgvWE3w2rGNAr_ti447cuI5RdFqXgYy6Pn-Imjbg");
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);

            conexao.connect();

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
            restaurantes = new ArrayList<>();
            RestauranteExibicao restaurante;

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject mObject = (JSONObject) jsonArray.get(i);
                restaurante = new RestauranteExibicao();

                restaurante.setId(mObject.getInt("id"));
//                restaurante.setEmail(mObject.getString("email"));
                restaurante.setFoto(mObject.getString("foto"));
                restaurante.setRazaoSocial(mObject.getString("razaoSocial"));

                restaurante.setTelefone(mObject.getString("telefone"));
                restaurante.setDistancia(mObject.getString("distancia"));
                restaurante.setTempoEntrega(mObject.getString("tempoEntrega"));
                restaurante.setNota(mObject.getString("nota"));

                restaurantes.add(restaurante);
            }

            TelaInicialActivity.restaurantes = restaurantes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
