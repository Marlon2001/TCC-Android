package godinner.lab.com.godinner.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Login;

public class LoginUsuario extends AsyncTask<Void, Void, String> {

    private Login login;
    private Context context;

    public LoginUsuario(Login login, Context context) {
        this.login = login;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        JSONStringer jsonLogin = new JSONStringer();

        try {
            jsonLogin.object();
            jsonLogin.key("email").value(login.getEmail());
            jsonLogin.key("password").value(login.getSenha());
            jsonLogin.endObject();

            URL url = new URL("http://"+ MainActivity.ipServidor+"/login/consumidor");

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
            return resposta;
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

    @Override
    protected void onPostExecute(String s) {

        JSONObject mObject = null;
        try {
            mObject = new JSONObject(s);

            if(mObject.getString("error")!=null){
                MainActivity.statusLogin = "Não cadastrado";
            } else {
                TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(context);
                mTokenUsuarioDAO.salvarToken(mObject.getString("token"));
                MainActivity.statusLogin = "Logou";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(s);
    }
}
