package com.mb.framework.web.exception;

/**
 * 
 * This class name is PreconditionFailedException used for exception handling
 *
 */
public final class PreconditionFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 */
	public PreconditionFailedException() {
        super();
    }

	/**
	 * argument constructor
	 * @param message
	 * @param cause
	 */
    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * argument constructor
     * @param message
     */
    public PreconditionFailedException(final String message) {
        super(message);
    }

    /**
     * argument constructor
     * @param cause
     */
    public PreconditionFailedException(final Throwable cause) {
        super(cause);
    }

}
