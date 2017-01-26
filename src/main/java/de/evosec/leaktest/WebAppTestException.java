package de.evosec.leaktest;

public class WebAppTestException extends Exception {

	private static final long serialVersionUID = 1L;

	public WebAppTestException() {
		super();
	}

	public WebAppTestException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebAppTestException(String message) {
		super(message);
	}

	public WebAppTestException(Throwable cause) {
		super(cause);
	}

}
