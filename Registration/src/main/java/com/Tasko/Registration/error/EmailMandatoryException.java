package com.Tasko.Registration.error;

public class EmailMandatoryException extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailMandatoryException() {
        super();
    }

    public EmailMandatoryException(String message) {
        super(message);
    }

    public EmailMandatoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailMandatoryException(Throwable cause) {
        super(cause);
    }

    protected EmailMandatoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
