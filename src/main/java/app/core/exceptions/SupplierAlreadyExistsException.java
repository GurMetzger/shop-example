package app.core.exceptions;

public class SupplierAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public SupplierAlreadyExistsException() {
		super();
	}

	public SupplierAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SupplierAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SupplierAlreadyExistsException(String message) {
		super(message);
	}

	public SupplierAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
