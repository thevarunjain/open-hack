package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "EMPLOYEE_NOT_FOUND";

    private Long id;

    public EmployeeNotFoundException(final Long id) {
        super("Employee not found.");
        this.id = id;
    }
}

