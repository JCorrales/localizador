package py.una.sgf.localizador.util;


/**
 * @author  dcerrano<cerrano.diego@gmail.com>
 * @since  1.0 27/11/15
 */
public interface AsyncCallback<T> {

	void onComplete(boolean success, T result, Throwable caught);

}
