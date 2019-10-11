package godinner.lab.com.godinner.utils;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Imagens extends AsyncTask<URL, Void, Drawable>{

    public static Drawable drawable;

    @Override
    protected Drawable doInBackground(URL... urls) {
        URL url = urls[0];
        InputStream content;

        try{
            content = (InputStream) url.getContent();

            this.drawable = Drawable.createFromStream(content, "src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
