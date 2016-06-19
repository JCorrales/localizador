package py.una.sgf.localizador.service;

public class ServiceResponse<T> {

	public static final boolean SUCCESS = true;
	public static final boolean UNSUCCESS = false;
	private T content;
	private boolean success;
	private String message;
	private String messageCode;

	public T getContent() {

		return content;
	}

	public void setContent(T content) {

		this.content = content;
	}

	public boolean isSuccess() {

		return success;
	}

	public void setSuccess(boolean success) {

		this.success = success;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getMessageCode() {

		return messageCode;
	}

	public void setMessageCode(String messageCode) {

		this.messageCode = messageCode;
	}

}
