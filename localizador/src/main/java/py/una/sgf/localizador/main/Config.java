package py.una.sgf.localizador.main;

/**
 * Created by znz on 29/01/16.
 */
public class Config {

    public static final String FACULTAD_LIST_URL = "http://ws.cnc.una.py:8090/ealuservice/facultades";

    public static final String URL_BASE="http://192.168.0.10/";

    public static final String VERIFICAR_SESION_URL = URL_BASE +"check-session";
    public static final String LOGIN_URL = URL_BASE +"admin/login/?";
    public static final String ALUMNO_LIST_URL = URL_BASE + "alumnos/?format=json";
    public static final String ALUMNO_DETALLE_URL = URL_BASE + "alumnos/?format=json";
    public static final String LOCALIZADOR_URL = URL_BASE + "sgf/localizador/send_position";

}
