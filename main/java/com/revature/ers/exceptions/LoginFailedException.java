package com.revature.ers.exceptions;


public class LoginFailedException extends NullPointerException {
    private static final long serialVersionUID = 1L;

	public LoginFailedException() {
    }

    public LoginFailedException(String s) {
        super(s);
    }
}
