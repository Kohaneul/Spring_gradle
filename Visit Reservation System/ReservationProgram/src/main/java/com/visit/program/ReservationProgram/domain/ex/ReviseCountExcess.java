package com.visit.program.ReservationProgram.domain.ex;

public class ReviseCountExcess extends RuntimeException{
    public ReviseCountExcess() {
        super();
    }

    public ReviseCountExcess(String message) {
        super(message);
    }

    public ReviseCountExcess(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviseCountExcess(Throwable cause) {
        super(cause);
    }

}
