package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import godinner.lab.com.godinner.MainActivity;

public class SuporteUsuario extends AsyncTask<Void, Void, String> {

    private String token;
    private int idConsumidor;
    private ResultRequest resultRequest;

    public SuporteUsuario(String token, int idConsumidor, @NonNull ResultRequest resultRequest) {
        this.token = token;
        this.idConsumidor = idConsumidor;
        this.resultRequest = resultRequest;
    }

    @Override
    protected String doInBackground(Void... voids) {
        JSONStringer jsonSuporte = new JSONStringer();

        try {
            jsonSuporte.object();
            jsonSuporte.key("idConsumidor").value(idConsumidor);
            jsonSuporte.key("token").value(token);
            jsonSuporte.endObject();

            URL url = new URL(MainActivity.ipServidorChat + "/suporte-usuario");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);

//            conexao.setRequestProperty("idConsumidor", idConsumidor);
//            conexao.setRequestProperty("token", token);

            PrintStream outputStream = new PrintStream(conexao.getOutputStream());
            outputStream.print(jsonSuporte);

            conexao.connect();
            Scanner scanner = new Scanner(conexao.getInputStream());

            return scanner.nextLine();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        resultRequest.onResult(str);
    }

    public interface ResultRequest {
        void onResult(String mObject);
    }
}
