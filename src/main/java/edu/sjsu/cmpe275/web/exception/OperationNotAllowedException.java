package edu.sjsu.cmpe275.web.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class OperationNotAllowedException extends ConstraintViolationException {
    private final String ERROR_CODE = "OPERATION_NOT_ALLOWED";

    public OperationNotAllowedException(final String message, final String parameter) {
        super(message, parameter);
    }
}