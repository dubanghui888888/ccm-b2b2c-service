package com.mb.framework.web.exception;

public final class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public ConflictException() {
        super();
    }

	/**
	 * argument constructor
	 * @param message
	 * @param cause
	 */
    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * argument constructor
     * @param message
     */
    public ConflictException(final String message) {
        super(message);
    }

    /**
     * argument constructor
     * @param cause
     */
    public ConflictException(final Throwable cause) {
        super(cause);
    }

}
