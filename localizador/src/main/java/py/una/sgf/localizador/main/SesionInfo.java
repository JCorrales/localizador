package py.una.sgf.localizador.main;

/**
 * Created by znz on 29/01/16.
 */
public class SesionInfo {

    private String csrfToken;
    private String username;
    private boolean logged;

    public SesionInfo(String csrfToken, String username, boolean logged) {
        this.csrfToken = csrfToken;
        this.username = username;
        this.logged = logged;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
