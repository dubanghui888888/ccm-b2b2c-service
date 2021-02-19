package com.mb.framework.exception;

public final class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String errorCode;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public SystemException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 */
	public SystemException(String message) {
		super(message);
		errorCode = message;
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of (cause==null ? null : cause.toString()) (which typically contains the
	 * class and detail message of cause).
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * This method is used for get error code
	 * 
	 * @return String
	 */
	public String getErrorCode() {
		String error = null;
		if (errorCode != null && errorCode.startsWith("error.")) {
			error = errorCode;
		}
		return error;
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            Throwable.getMessage() method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
