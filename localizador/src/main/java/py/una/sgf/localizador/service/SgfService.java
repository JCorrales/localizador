package py.una.sgf.localizador.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import py.una.sgf.localizador.util.AsyncCallback;
import py.una.sgf.localizador.util.ServerCallAsync;

/**
 * Created by znz on 29/01/16.
 */
public class SgfService {

    private static final String TAG = SgfService.class.getCanonicalName();
    public static String cedula;
    public static String servidorIp;

    public void sendData(final AsyncCallback<ServiceResponse<String>> asyncCallback, String cedula, String servidorIp) {
        SgfService.cedula = cedula;
        SgfService.servidorIp = servidorIp;
        ServerCallAsync server = new ServerCallAsync() {
            @Override
            protected void onPostExecute(String response, int responseCode) {
                try {
                    Log.i(TAG, "RESPONSE CODE: " + responseCode);
                    ServiceResponse<String> serviceResponse = new ServiceResponse();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        serviceResponse = getServiceResponseFromJson(response);
                        asyncCallback.onComplete(serviceResponse.isSuccess(), serviceResponse, null);
                    } else {
                        throw new Exception("Error: "+serviceResponse.getMessage());
                    }

                } catch (Exception exc) {
                    asyncCallback.onComplete(false, null, exc);
                }
            }
        };

        //Map<String, String> params = new HashMap<>();
       // params.put("cedula", cedula);
        server.setPost(false);
       // server.setPostDataParams(params);

        server.execute("http://" + servidorIp + "/localizador/registrar?cedula="+cedula);

    }

    private ServiceResponse<String> getServiceResponseFromJson(String response)
            throws JSONException {
        Log.i(TAG, "Procesando respuesta: " + response);
        JSONObject responseJO = new JSONObject(response);
        ServiceResponse<String> serviceResponse = new ServiceResponse();

        serviceResponse.setMessage(responseJO.getString("message"));
        serviceResponse.setSuccess(responseJO.getBoolean("success"));

        return serviceResponse;
    }

}
