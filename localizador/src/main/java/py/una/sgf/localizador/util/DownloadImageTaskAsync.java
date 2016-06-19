package py.una.sgf.localizador.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * @author dcerrano<cerrano.diego@gmail.com>
 * @since 1.0 27/11/15
 */
public class DownloadImageTaskAsync extends ServerCallAsyncAncestor<Bitmap> {

    private static final String TAG = DownloadImageTaskAsync.class.getCanonicalName();
    private ImageView imageView;

    public DownloadImageTaskAsync() {

    }
    public DownloadImageTaskAsync(ImageView imageView) {
        this.imageView = imageView;
    }
    protected Bitmap getResult(InputStream inputStream, int responseCode) {
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            return BitmapFactory.decodeStream(inputStream);
        }else {
            return null;
        }
    }
    protected void onPostExecute(Bitmap result) {
        if (imageView != null && result != null) {
            imageView.setImageBitmap(result);
        }
    }
}
