package godinner.lab.com.godinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import godinner.lab.com.godinner.dao.CidadeEstadoDAO;
import godinner.lab.com.godinner.dao.ConsumidorDAO;
import godinner.lab.com.godinner.dao.TokenUsuarioDAO;
import godinner.lab.com.godinner.model.Cidade;
import godinner.lab.com.godinner.model.Estado;
import godinner.lab.com.godinner.tasks.BuscarCidades;
import godinner.lab.com.godinner.tasks.BuscarEstados;

public class SplashActivity extends Activity {

    public static ArrayList<Estado> estados;
    public static ArrayList<Cidade> cidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Glide.with(this).load(R.drawable.logo2).into((ImageView) findViewById(R.id.imageView));


//        final TokenUsuarioDAO mTokenUsuarioDAO = new TokenUsuarioDAO(SplashActivity.this);
//        final CidadeEstadoDAO mCidadeEstadoDAO = new CidadeEstadoDAO(SplashActivity.this);
//
//        if(!mCidadeEstadoDAO.EstadoAlreadyPopuled()) {
//            try {
//                BuscarEstados mEstados = new BuscarEstados();
//                mEstados.execute();
//                mEstados.get();
//                mCidadeEstadoDAO.addEstados(estados);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if(!mCidadeEstadoDAO.CidadeAlreadyPopuled()){
//            try {
//                BuscarCidades mCidades = new BuscarCidades();
//                mCidades.execute();
//                mCidades.get();
//                mCidadeEstadoDAO.addCidades(cidades);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, 1000);
    }
}
