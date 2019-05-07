package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.OrganizationMembershipNotFoundException;
import edu.sjsu.cmpe275.domain.repository.OrganizationMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class OrganizationMembershipService {
    private OrganizationMembershipRepository organizationMembershipRepository;

    @Autowired
    public OrganizationMembershipService(
            final OrganizationMembershipRepository organizationMembershipRepository
    ) {
        this.organizationMembershipRepository = organizationMembershipRepository;
    }

    public OrganizationMembership findOrganizationMembership(
            final Organization organization,
            final User member
    ) {
        // This is used to find particular combination of organization and member ID
        OrganizationMembership.OrganizationMembershipId toFind =
                OrganizationMembership.OrganizationMembershipId.builder()
                        .organizationId(organization.getId())
                        .memberId(member.getId())
                        .build();
        return organizationMembershipRepository.findById(toFind)
                .orElseThrow(() -> new OrganizationMembershipNotFoundException(toFind));
    }

    public List<OrganizationMembership> findApprovedOrganizationMemberships(final Organization organization) {
        // This is used to find all approved membership of given organization
        List<OrganizationMembership> approvedMembershipList =
                organizationMembershipRepository.findByOrganizationIdAndStatus(
                        organization.getId(),
                        "Approved"
                );
        return approvedMembershipList;
    }

    public List<OrganizationMembership> findOrganizationMemberships(final Organization organization) {
        List<OrganizationMembership> membershipList =
                organizationMembershipRepository.findByOrOrganizationId(organization.getId());
        return membershipList;
    }

    @Transactional
    public OrganizationMembership createOrganizationMembership(final OrganizationMembership newOrganizationMembership) {
        return organizationMembershipRepository.save(newOrganizationMembership);
    }

    @Transactional
    public OrganizationMembership updateOrganizationMembership(
            final Organization organization,
            final User member,
            final String toState
    ) {
        OrganizationMembership existingOrganizationMembership = findOrganizationMembership(organization, member);
        existingOrganizationMembership.setStatus(toState);
        return existingOrganizationMembership;
    }
}
