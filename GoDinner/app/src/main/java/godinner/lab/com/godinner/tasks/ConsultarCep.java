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

import godinner.lab.com.godinner.Cadastro3Activity;
import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.SplashActivity;
import godinner.lab.com.godinner.model.Endereco;
import godinner.lab.com.godinner.model.Estado;

public class ConsultarCep extends AsyncTask {

    private String cep;
    private Endereco endereco;

    public ConsultarCep(String cep) {
        this.cep = cep;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL(MainActivity.ipServidor+"/endereco/cep/"+cep);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoInput(true);

            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";
            String dados = "";

            while (linha != null){
                linha = bufferedReader.readLine();
                dados = dados + linha;
            }

            JSONObject mObject = new JSONObject(dados);
            endereco = new Endereco();
            endereco.setLogradouro(mObject.getString("logradouro"));
            endereco.setBairro(mObject.getString("bairro"));
            endereco.setCep(mObject.getString("cep"));
            endereco.setIdCidade(mObject.getJSONObject("cidade").getInt("id"));
            endereco.setCidadeNome(mObject.getJSONObject("cidade").getString("cidade"));
            endereco.setIdEstado(mObject.getJSONObject("cidade").getJSONObject("estado").getInt("id"));
            endereco.setEstadoNome(mObject.getJSONObject("cidade").getJSONObject("estado").getString("estado"));

            Cadastro3Activity.endereco = endereco;
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
