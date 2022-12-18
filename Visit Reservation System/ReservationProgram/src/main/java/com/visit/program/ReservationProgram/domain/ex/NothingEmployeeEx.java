package com.visit.program.ReservationProgram.domain.ex;

public class NothingEmployeeEx extends RuntimeException{
    public NothingEmployeeEx() {
        super();
    }

    public NothingEmployeeEx(String message) {
        super(message);
    }

    public NothingEmployeeEx(String message, Throwable cause) {
        super(message, cause);
    }

    public NothingEmployeeEx(Throwable cause) {
        super(cause);
    }

}
