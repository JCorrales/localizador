package py.una.sgf.localizador.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import py.una.sgf.localizador.main.Localizador;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

public class LocalizadorService extends Service implements OnConnectionFailedListener, ConnectionCallbacks{

    private static final String TAG = Localizador.class.getCanonicalName();
    private GoogleApiClient mGoogleApiClient;
    private String cedula;
    private String servidorIp;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 30000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //onStartCommand puede ser llamado por android despues
        //en ese caso intent sera null
        try{
            if(intent != null){
                cedula = intent.getStringExtra("cedula");
                servidorIp = intent.getStringExtra("serverIp");
            }

            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
            }
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
            }
        }catch(Throwable ex){
            ex.printStackTrace();
        }


        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "ERROR AL CONECTAR: " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(Bundle bundle) {
        //initializeTimerTask(sharedPreferences);
        //timer.schedule(timerTask, 1000, 5000);
        /*SharedPreferences sharedPreferences = getSharedPreferences("position", Context.MODE_PRIVATE);
        locationListener.cedula = sharedPreferences.getString("cedula", "9999");
        locationListener.servidorIp = sharedPreferences.getString("servidorIp", "9999");
        LocationRequest request = LocationRequest.create();
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setMaxWaitTime(5000);
        request.setNumUpdates(10000);
        //locationManager = (LocationManager)
        //      getSystemService(Context.LOCATION_SERVICE);
        PendingResult<Status> result = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, locationListener);
        //locationManager.requestLocationUpdates(
        //  locationManager.getAllProviders().get(0), 0, 0, locationListener);*/
        try{
            LocationReceiver.cedula = this.cedula;
            LocationReceiver.servidorIp = this.servidorIp;
            LocationRequest request = LocationRequest.create();
            request.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            request.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            Intent intent = new Intent(this.getApplicationContext(), LocationReceiver.class);
            PendingIntent locationIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request , locationIntent);

            Log.i(TAG, "despues del request cedula:" + LocationReceiver.cedula);
        }catch (Throwable ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}
