package com.Tasko.Registration.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserName_Paswword_Incorrect extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserName_Paswword_Incorrect() {
        super();
    }

    public UserName_Paswword_Incorrect(String message) {
        super(message);
    }

    public UserName_Paswword_Incorrect(String message, Throwable cause) {
        super(message, cause);
    }

    public UserName_Paswword_Incorrect(Throwable cause) {
        super(cause);
    }

    protected UserName_Paswword_Incorrect(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
