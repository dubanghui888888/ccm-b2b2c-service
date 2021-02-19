package com.mb.framework.web.exception;

public final class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public BadRequestException() {
        super();
    }

	/**
	 * 
	 * @param message
	 * @param cause
	 */
    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message
     */
    public BadRequestException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause
     */
    public BadRequestException(final Throwable cause) {
        super(cause);
    }

}
