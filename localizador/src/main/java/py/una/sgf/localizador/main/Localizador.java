package py.una.sgf.localizador.main;




import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;


import py.una.sgf.localizador.util.ServerCallAsync;

/**
 * Created by juan on 27/04/16.
 */
public class Localizador implements LocationListener {
    public String servidorIp;
    public String cedula;

    @Override
    public void onLocationChanged(Location location) {
        try{
            ServerCallAsync server = new ServerCallAsync();
            server.setPost(false);

            server.execute("http://" + servidorIp + "/localizador/send_position?cedula=" +
                    cedula + "&longitud=" + location.getLongitude() + "&latitud=" + location.getLatitude());
            Log.i(Localizador.class.getCanonicalName(), "enviando posicion: a " + "http://" + servidorIp + "/sgf/localizador/send_position");
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
