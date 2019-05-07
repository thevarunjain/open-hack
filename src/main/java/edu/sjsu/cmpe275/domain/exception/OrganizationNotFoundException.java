package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrganizationNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "ORGANIZATION_NOT_FOUND";

    private Long id;

    public OrganizationNotFoundException(final Long id) {
        super("Organization not found.");
        this.id = id;
    }
}
