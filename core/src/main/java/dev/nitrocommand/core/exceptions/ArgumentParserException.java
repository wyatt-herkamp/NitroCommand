package dev.nitrocommand.core.exceptions;

public class ArgumentParserException extends Exception {

    public ArgumentParserException() {
    }

    public ArgumentParserException(String message) {
        super(message);
    }

    public ArgumentParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentParserException(Throwable cause) {
        super(cause);
    }

    public ArgumentParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
