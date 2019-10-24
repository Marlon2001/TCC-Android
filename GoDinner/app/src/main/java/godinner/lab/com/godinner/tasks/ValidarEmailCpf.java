package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import godinner.lab.com.godinner.MainActivity;

public class ValidarEmailCpf extends AsyncTask<Void, Void, Boolean> {

    private ValidarCampo validarCampo;
    private String url;

    public ValidarEmailCpf(String tipo, String data, ValidarCampo validarCampo) {
        this.validarCampo = validarCampo;
        switch (tipo) {
            case "cpf":
                url = MainActivity.ipServidor + "/consumidor/valida/cpf/" + data;
                break;
            case "email":
                url = MainActivity.ipServidor + "/consumidor/valida/email/" + data;
                break;
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL(this.url);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conexao.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String dados = bufferedReader.readLine();

            return Boolean.parseBoolean(dados);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (this.validarCampo != null) {
            validarCampo.Request(b);
        }
    }

    public interface ValidarCampo {
        void Request(Boolean result);
    }
}
