package com.mb.framework.exception;

/**
 * This Exception class will be used in DAO class to wrap hibernate/sql
 * exception.
 * 
 */

public class DAOException extends Exception
{

	private static final long serialVersionUID = -810284859715336800L;

	private String errorCode;

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 */
	public DAOException(String message)
	{
		super(message);

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
	public DAOException(Throwable cause)
	{
		super(cause);

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
	public DAOException(String message, Throwable cause)
	{
		super(message, cause);

	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode()
	{
		String error =null;
		if (errorCode != null && errorCode.startsWith("message.error.")){
			error = errorCode;
		}
		return error;
	}

}
