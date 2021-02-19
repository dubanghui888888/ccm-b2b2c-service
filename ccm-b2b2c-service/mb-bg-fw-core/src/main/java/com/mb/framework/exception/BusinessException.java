package com.mb.framework.exception;

public class BusinessException extends Exception 
{
    private static final long serialVersionUID = -810284859715336800L;
    private String errorCode;
    
    /**
     * Constructs a new exception with null as its detail message.
     */
    public BusinessException() {
        super();
    }
    
    /**
     * Constructs a new exception with the specified cause and a detail message of
     *  (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
     *  (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message. 
     * The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public BusinessException(String message) 
    {
        super();
        errorCode = message;
    }
    
    /**
     * 
     * This method is used to get error code
     * @return
     */
    public String getErrorCode()
    {
    	return errorCode;
    }
    
    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval 
     * by the Throwable.getCause() method).
     *  (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public BusinessException(String message, Throwable cause) {
        super(cause);
        errorCode = message;
    }
}