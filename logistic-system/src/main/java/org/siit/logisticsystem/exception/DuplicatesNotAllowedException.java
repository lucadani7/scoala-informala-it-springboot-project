package org.siit.logisticsystem.exception;

public class DuplicatesNotAllowedException extends RuntimeException {
    public DuplicatesNotAllowedException(String message) {
        super(message);
    }
}
