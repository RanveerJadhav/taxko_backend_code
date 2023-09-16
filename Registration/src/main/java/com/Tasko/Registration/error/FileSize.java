package com.Tasko.Registration.error;

public class FileSize extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileSize() {
        super();
    }

    public FileSize(String message) {
        super(message);
    }

    public FileSize(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSize(Throwable cause) {
        super(cause);
    }

    protected FileSize(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
