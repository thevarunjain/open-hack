package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "USER_NOT_FOUND";

    private Long id;

    public UserNotFoundException(final Long id) {
        super("User not found.");
        this.id = id;
    }
}

