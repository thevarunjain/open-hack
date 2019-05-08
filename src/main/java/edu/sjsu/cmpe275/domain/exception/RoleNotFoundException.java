package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RoleNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "ROLE_NOT_FOUND";

    private String role;

    public RoleNotFoundException(final String role) {
        super("Role not found.");
        this.role = role;
    }
}
