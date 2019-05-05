package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMembershipMapper {
    public OrganizationMembership map(final Organization organization, final User member) {
        return OrganizationMembership.builder()
                .id(mapMembership(organization, member))
                .status("Pending")
                .build();
    }

    private OrganizationMembership.OrganizationMembershipId mapMembership(
            final Organization organization,
            final User member
    ) {
        return OrganizationMembership.OrganizationMembershipId.builder()
                .organizationId(organization.getId())
                .memberId(member.getId())
                .build();
    }
}
