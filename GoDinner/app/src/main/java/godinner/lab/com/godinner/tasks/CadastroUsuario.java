package godinner.lab.com.godinner.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import godinner.lab.com.godinner.MainActivity;
import godinner.lab.com.godinner.model.Cadastro;
import godinner.lab.com.godinner.model.Contato;
import godinner.lab.com.godinner.model.Endereco;

public class CadastroUsuario extends AsyncTask {

    private Cadastro cadastro;
    private Contato contato;
    private Endereco endereco;

    public CadastroUsuario(Cadastro cadastro, Contato contato, Endereco endereco) {
        this.cadastro = cadastro;
        this.contato = contato;
        this.endereco = endereco;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONStringer jsonCadastro = new JSONStringer();

        try {
            jsonCadastro.object();
            jsonCadastro.key("nome").value(contato.getNome());
            jsonCadastro.key("email").value(cadastro.getEmail());
            jsonCadastro.key("senha").value(cadastro.getSenha());
            jsonCadastro.key("cpf").value(contato.getCpf());
            jsonCadastro.key("telefone").value(contato.getTelefone());
            jsonCadastro.key("fotoPerfil").value(null);
            jsonCadastro.key("endereco").object()
                    .key("cep").value(endereco.getCep())
                    .key("numero").value(endereco.getNumero())
                    .key("logradouro").value(endereco.getLogradouro())
                    .key("bairro").value(endereco.getBairro())
                    .key("complemento").value(endereco.getComplemento())
                    .key("referencia").value(endereco.getReferencia())
                    .key("cidade").object()
                        .key("id").value(endereco.getIdCidade())
                    .endObject()
            .endObject();
            jsonCadastro.endObject();

            URL url = new URL("http://"+MainActivity.ipServidor+"/consumidor/novo");

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();


//            {"nome":"marlon","email":"marlon2@teste.com","senha":"12345678","cpf":"113.262.578-54","telefone":"1147075043","fotoPerfil":null,"endereco":{"cep":"06606300","numero":"76","logradouro":"Rua das Magnólias","bairro":"Vila Ipê","complemento":"","referencia":"","cidade":{"id":3525003}}}
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);

            PrintStream outputStream = new PrintStream(conexao.getOutputStream());
            outputStream.print(jsonCadastro);

            conexao.connect();

            Scanner scanner = new Scanner(conexao.getInputStream());
            String resposta = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
