package py.una.sgf.localizador.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;


/**
 * Created by juan on 15/06/16.
 */
public class PermisosManejador extends Activity{

    private static final String[] PERMISOS={
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final int LOCATION_REQUEST=1333;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!canAccessLocation()) {
            requestPermissions(PERMISOS, LOCATION_REQUEST);
        }

    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }


    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
}
