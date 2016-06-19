package py.una.sgf.localizador.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;


/**
 * @author dcerrano<cerrano.diego@gmail.com>
 * @since 1.0 27/11/15
 */
public class ServerCallAsync extends ServerCallAsyncAncestor<String> {
    private static final String TAG = ServerCallAsync.class.getCanonicalName();

    protected String getResult(InputStream inputStream, int responseCode) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer result = new StringBuffer("");
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception ex) {

        }

        return result.toString();
    }

}
