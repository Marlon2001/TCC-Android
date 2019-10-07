package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.model.Consumidor;
import godinner.lab.com.godinner.model.Endereco;

public class BuscarConsumidor  extends AsyncTask {

    private String token;
    private Consumidor mConsumidor;

    public BuscarConsumidor(String token) {
        this.token = token;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        mConsumidor = new Consumidor();
        try {
            URL url = new URL("http://"+MainActivity.ipServidor+"/consumidor/este");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("token", token);
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoInput(true);
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
            mConsumidor.setIdServidor(json.getInt("id"));
            mConsumidor.setNome(json.getString("nome"));
            mConsumidor.setEmail(json.getString("email"));
            mConsumidor.setCpf(json.getString("cpf"));
            mConsumidor.setTelefone(json.getString("telefone"));
            mConsumidor.setFotoPerfil(json.getString("fotoPerfil"));
            Endereco endereco = new Endereco();
            endereco.setIdEndereco(json.getJSONObject("endereco").getInt("id"));
            endereco.setCep(json.getJSONObject("endereco").getString("cep"));
            endereco.setNumero(json.getJSONObject("endereco").getString("numero"));
            endereco.setLogradouro(json.getJSONObject("endereco").getString("logradouro"));
            endereco.setBairro(json.getJSONObject("endereco").getString("bairro"));
            endereco.setComplemento(json.getJSONObject("endereco").getString("complemento"));
            endereco.setReferencia(json.getJSONObject("endereco").getString("referencia"));
            endereco.setIdCidade(json.getJSONObject("endereco").getJSONObject("cidade").getInt("id"));
            endereco.setIdEstado(json.getJSONObject("endereco").getJSONObject("cidade").getJSONObject("estado").getInt("id"));
            endereco.setCidadeNome(json.getJSONObject("endereco").getJSONObject("cidade").getString("cidade"));
            endereco.setCidadeNome(json.getJSONObject("endereco").getJSONObject("cidade").getJSONObject("estado").getString("estado"));
            mConsumidor.setEndereco(endereco);

            MainActivity.mConsumidorLogado = mConsumidor;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

