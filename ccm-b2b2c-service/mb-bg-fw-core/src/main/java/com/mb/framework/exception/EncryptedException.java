package com.mb.framework.exception;

public class EncryptedException extends Exception
{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3827186664467853684L;
    
    /**
     * Constructs a new exception with null as its detail message.
     */
    public EncryptedException() {
        super();
    }
    
    /**
     * Constructs a new exception with the specified cause and a detail message of
     *  (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
     *  (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public EncryptedException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message. 
     * The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public EncryptedException(String message) 
    {
        super(message);
    }
    
    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval 
     * by the Throwable.getCause() method).
     *  (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public EncryptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
