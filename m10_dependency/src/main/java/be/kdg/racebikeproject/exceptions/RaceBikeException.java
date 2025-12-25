package be.kdg.racebikeproject.exceptions;

public class RaceBikeException extends RuntimeException {
    public RaceBikeException() {}

    public RaceBikeException(String message) {
        super(message);
    }

    public RaceBikeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RaceBikeException(Throwable cause) {
        super(cause);
    }

    public RaceBikeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
