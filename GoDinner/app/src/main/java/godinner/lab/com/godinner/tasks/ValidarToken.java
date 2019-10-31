package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import godinner.lab.com.godinner.MainActivity;

public class ValidarToken extends AsyncTask<Void, Void, Boolean> {

    private ResultRequest mListener;
    private String token;

    public ValidarToken(String token, @NonNull ResultRequest mListener) {
        this.token = token;
        this.mListener = mListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL(MainActivity.ipServidor + "/consumidor/este");

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

            while (linha != null) {
                dados = dados + linha;
                linha = bufferedReader.readLine();
            }

            JSONObject json = new JSONObject(dados);

            if (json.has("status") && json.has("error")) {
                int status = json.getInt("status");
                String error = json.getString("error");

                if (status == 401 && error.equals("Unauthorized")) {
                    return false;
                }
            } else if (json.length() == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mListener != null)
            mListener.Request(result);
    }

    public interface ResultRequest {
        void Request(boolean result);
    }
}
