package com.txlpsns.api;

public class BookDoesNotExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookDoesNotExistException(String message) {
        super(message);
    }

}
