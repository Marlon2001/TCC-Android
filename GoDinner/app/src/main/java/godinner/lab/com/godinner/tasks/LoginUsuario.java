package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.model.Login;

public class LoginUsuario extends AsyncTask {

    private Login login;

    public LoginUsuario(Login login) {
        this.login = login;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONStringer jsonLogin = new JSONStringer();

        try {
            jsonLogin.object();
            jsonLogin.key("email").value(login.getEmail());
            jsonLogin.key("senha").value(login.getSenha());
            jsonLogin.endObject();

            URL url = new URL("http://"+ MainActivity.ipServidor+"/consumidor/login");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);

            PrintStream outputStream = new PrintStream(conexao.getOutputStream());
            outputStream.print(jsonLogin);

            conexao.connect();

            Scanner scanner = new Scanner(conexao.getInputStream());
            String resposta = scanner.nextLine();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
