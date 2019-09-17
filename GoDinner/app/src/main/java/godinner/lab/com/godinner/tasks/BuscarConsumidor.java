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
import java.net.URL;
import java.util.ArrayList;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.TelaInicialActivity;
import godinner.lab.com.godinner.model.Categoria;
import godinner.lab.com.godinner.model.Consumidor;

public class BuscarConsumidor  extends AsyncTask<Void, Void, Consumidor> {
    private String token;
    private Consumidor mThisConsumidor = new Consumidor();

    public BuscarConsumidor(String token) {
        this.token = token;
    }


    private ArrayList<Categoria> categorias;




    @Override
    protected Consumidor doInBackground(Void... voids) {
        try {


//            jsonCadastro.object();
//            jsonCadastro.key("token").value(this.token);
//            jsonCadastro.endObject();

            URL url = new URL("http://" + MainActivity.ipServidor + "/consumidor/este");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

//            conexao.setRequestProperty("Content-Type", "application/json");

            conexao.setRequestProperty("token", this.token);

//            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);


//            PrintStream outputStream = new PrintStream(conexao.getOutputStream());


            conexao.connect();


            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";
            String dados = "";

            while (linha != null){
                dados = dados + linha;
                linha = bufferedReader.readLine();
            }

            JSONObject json = new JSONObject(dados);

            mThisConsumidor.setId(json.getInt("id"));
            mThisConsumidor.setIdEndereco(json.getJSONObject("endereco").getInt("id"));
            mThisConsumidor.setFotoPerfil(json.getString("fotoPerfil"));
            mThisConsumidor.setEmail(json.getString("email"));
            mThisConsumidor.setNome(json.getString("nome"));
            MainActivity.mConsumidorLogado = mThisConsumidor;


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
        }

        return mThisConsumidor;

        }


}

