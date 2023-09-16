package com.Tasko.Registration.error;

public class OtpNotVaild extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OtpNotVaild() {
        super();
    }

    public OtpNotVaild(String message) {
        super(message);
    }

    public OtpNotVaild(String message, Throwable cause) {
        super(message, cause);
    }

    public OtpNotVaild(Throwable cause) {
        super(cause);
    }

    protected OtpNotVaild(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
