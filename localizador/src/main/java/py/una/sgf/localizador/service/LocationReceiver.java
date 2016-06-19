package py.una.sgf.localizador.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import py.una.sgf.localizador.util.ServerCallAsync;



/**
 * Created by juan on 17/06/16.
 */
public class LocationReceiver extends BroadcastReceiver {
    public static String servidorIp;
    public static String cedula;
    @Override
    public void onReceive(Context context, Intent intent) {
        LocationResult locationResult = LocationResult.extractResult(intent);
        if(locationResult == null){
            return;
        }
        int newest = -1;
        newest = locationResult.getLocations().size() -1 ;
        Location location = locationResult.getLocations().get(newest);

        try{
            if(location == null) {
                return;
            }
            ServerCallAsync server = new ServerCallAsync();
            server.setPost(false);

            server.execute("http://" + servidorIp + "/localizador/send_position?cedula=" +
                    cedula + "&longitud=" + location.getLongitude() + "&latitud=" + location.getLatitude());
            Log.i(LocationReceiver.class.getCanonicalName(), "enviando posicion: a " + "http://" + servidorIp + "/sgf/localizador/send_position");

        }catch (Throwable t){
            t.printStackTrace();
        }
    }
}
