package com.tiptop.users.exception;
//TODO handle exception
public class NoTicketFoundException extends RuntimeException{

    public NoTicketFoundException() {
    }

    public NoTicketFoundException(String message) {
        super(message);
    }

    public NoTicketFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTicketFoundException(Throwable cause) {
        super(cause);
    }

    public NoTicketFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
