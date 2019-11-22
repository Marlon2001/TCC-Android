package godinner.lab.com.godinner.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {

    public static String getHoraAtual(){
        Date data = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEE hh:mm aaa");
        String dataFormatada = dateFormat.format(data);

        return dataFormatada;
    }
}
