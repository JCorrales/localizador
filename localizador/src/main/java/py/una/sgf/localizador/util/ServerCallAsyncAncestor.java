package py.una.sgf.localizador.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * @author dcerrano<cerrano.diego@gmail.com>
 * @since 1.0 27/11/15
 */
public abstract class ServerCallAsyncAncestor<Result> extends AsyncTask<String, Void, Result> {

    private static final String TAG = ServerCallAsyncAncestor.class.getCanonicalName();
    private int responseCode;
    private int readTimeout = 10000;
    private int connectTimeout = 10000;
    private boolean doInput = true;
    private boolean doOutput = true;
    protected boolean postMethod;
    private Map<String, String> postDataParams;
    private boolean writeOutputStream;

    @Override
    protected Result doInBackground(String... urls) {
        String urlString = urls[0];
        Result result = null;
        try {
            Log.d(TAG, "INVOCANDO URL: " + urlString);
            URL url = new URL(urlString);

            result = createConnection(  url);
        } catch (Exception exc) {

            Log.e(TAG, "Error:" + exc);
        }

        return result;
    }

    protected Result createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(getReadTimeout());
        connection.setConnectTimeout(getConnectTimeout());

        connection.setDoInput(isDoInput());

        if (isPostMethod() || getPostDataParams()!= null) {
            connection.setRequestMethod("POST");
            writePostParams(connection);

        }else {
            connection.setRequestMethod("GET");
        }

        connection.connect();
        responseCode = connection.getResponseCode();
        Result result = getResult(connection.getInputStream(), responseCode);
        connection.disconnect();

        return result;
    }

    protected void writePostParams(HttpURLConnection connection) throws IOException {

            connection.setDoOutput(isDoOutput());
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(getPostDataParams()));

            writer.flush();
            writer.close();
            outputStream.close();

    }

    protected String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    protected abstract Result getResult(InputStream inputStream, int responseCode);

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    protected void onPostExecute(Result result) {
        onPostExecute(result, responseCode);
    }

    protected void onPostExecute(Result result, int responseCode) {
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public boolean isDoInput() {
        return doInput;
    }

    public void setDoInput(boolean doInput) {
        this.doInput = doInput;
    }

    public boolean isDoOutput() {
        return doOutput;
    }

    public void setDoOutput(boolean doOutput) {
        this.doOutput = doOutput;
    }

    public void setPost(boolean post) {
        this.postMethod = post;
    }

    public boolean isPostMethod() {
        return postMethod;
    }

    public Map<String, String> getPostDataParams() {
        return postDataParams;
    }

    public void setPostDataParams(Map<String, String> postDataParams) {
        this.postDataParams = postDataParams;
    }
}
