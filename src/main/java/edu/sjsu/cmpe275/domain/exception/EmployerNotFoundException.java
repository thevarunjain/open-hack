package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EmployerNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "EMPLOYER_NOT_FOUND";

    private Long id;

    public EmployerNotFoundException(final Long id) {
        super("Employer not found.");
        this.id = id;
    }
}

