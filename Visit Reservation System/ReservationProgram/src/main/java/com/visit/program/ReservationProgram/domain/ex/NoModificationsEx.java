package com.visit.program.ReservationProgram.domain.ex;

public class NoModificationsEx extends RuntimeException{
    public NoModificationsEx() {
        super();
    }

    public NoModificationsEx(String message) {
        super(message);
    }

    public NoModificationsEx(String message, Throwable cause) {
        super(message, cause);
    }

    public NoModificationsEx(Throwable cause) {
        super(cause);
    }

    protected NoModificationsEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
