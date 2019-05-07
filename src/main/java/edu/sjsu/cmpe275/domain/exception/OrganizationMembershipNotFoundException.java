package edu.sjsu.cmpe275.domain.exception;

import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrganizationMembershipNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "ORGANIZATION_MEMBERSHIP_NOT_FOUND";

    private OrganizationMembership.OrganizationMembershipId id;

    public OrganizationMembershipNotFoundException(
            final OrganizationMembership.OrganizationMembershipId id
    ) {
        super("Organization membership not found.");
        this.id = id;
    }
}
