package py.una.sgf.localizador.main;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by znz on 29/01/16.
 */
public class CursoOwlApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }
}
