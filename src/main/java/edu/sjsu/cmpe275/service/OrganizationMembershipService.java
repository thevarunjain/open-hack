package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.repository.OrganizationMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class OrganizationMembershipService {
    private OrganizationMembershipRepository organizationMembershipRepository;

    @Autowired
    public OrganizationMembershipService(
            final OrganizationMembershipRepository organizationMembershipRepository
    ) {
        this.organizationMembershipRepository = organizationMembershipRepository;
    }

    @Transactional
    public void createOrganizationMembership(final OrganizationMembership newOrganizationMembership) {
        organizationMembershipRepository.save(newOrganizationMembership);
    }
}
