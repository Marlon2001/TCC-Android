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

public class BuscarConsumidor  extends AsyncTask {
    private String token;
    private Consumidor mThisConsumidor = null;

    public BuscarConsumidor(String token) {
        this.token = token;
    }


    private ArrayList<Categoria> categorias;

    @Override
    protected Object doInBackground(Object[] o) {
        try {
            JSONStringer jsonCadastro = new JSONStringer();

            jsonCadastro.object();
            jsonCadastro.key("token").value(this.token);

            jsonCadastro.endObject();

            URL url = new URL("http://" + MainActivity.ipServidor + "/consumidor/este");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);

            PrintStream outputStream = new PrintStream(conexao.getOutputStream());
            outputStream.print(jsonCadastro);

            conexao.connect();


            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";
            String dados = "";

            while (linha != null) {
                linha = bufferedReader.readLine();
                dados = dados + linha;
            }

            JSONObject json = new JSONObject(dados);

            mThisConsumidor.setId(json.getInt("id"));
            mThisConsumidor.setIdEndereco(json.getJSONObject("id_endereco").getInt("id"));
            mThisConsumidor.setFotoPerfil(json.getString("foto"));
            mThisConsumidor.setEmail(json.getString("foto"));

            TelaInicialActivity.categorias = categorias;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
        }

        return mThisConsumidor;

        }



}

