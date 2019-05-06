package com.simokhov.graph;

/**
 * Simple package Exception
 */
public class GraphRuntimeException extends RuntimeException {

    public GraphRuntimeException() {
        super();
    }

    public GraphRuntimeException(String message) {
        super(message);
    }

    public GraphRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphRuntimeException(Throwable cause) {
        super(cause);
    }

    protected GraphRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
