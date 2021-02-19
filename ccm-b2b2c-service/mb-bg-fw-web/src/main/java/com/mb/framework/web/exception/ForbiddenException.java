package com.mb.framework.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when user is forbidden to execute specified operation or access specified data.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 */
	public ForbiddenException() {
        super();
    }

	/**
	 * argument constructor
	 * @param message
	 */
    public ForbiddenException(final String message) {
        super(message);
    }

    /**
     * argument constructor
     * @param cause
     */
    public ForbiddenException(final Throwable cause) {
        super(cause);
    }

}