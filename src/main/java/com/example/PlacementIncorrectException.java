package com.example;
public class PlacementIncorrectException extends RuntimeException {
    public PlacementIncorrectException() {
    }

    public PlacementIncorrectException( String message ) {
        super( message );
    }

    public PlacementIncorrectException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PlacementIncorrectException( Throwable cause ) {
        super( cause );
    }

    public PlacementIncorrectException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
