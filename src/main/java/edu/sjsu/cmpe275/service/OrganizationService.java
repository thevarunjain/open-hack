package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.OrganizationNotFoundException;
import edu.sjsu.cmpe275.domain.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrganizationService {
    private OrganizationRepository organizationRepository;

    private UserService userService;

    private OrganizationMembershipService organizationMembershipService;

    @Autowired
    public OrganizationService(
            final OrganizationRepository organizationRepository,
            final UserService userService,
            final OrganizationMembershipService organizationMembershipService
    ) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
        this.organizationMembershipService = organizationMembershipService;
    }

    public List<Organization> findOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization findOrganization(final Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    @Transactional
    public Organization createOrganization(
            final Organization organization,
            final Long ownerId
    ) {
        User owner = userService.findUser(ownerId);
        organization.setOwner(owner);
        return organizationRepository.save(organization);
    }

    public List<User> findApprovedOrganizationMembers(final Long id) {
        Organization organization = findOrganization(id);
        List<OrganizationMembership> approvedOrganizationMemberShipList =
                organizationMembershipService.findApprovedOrganizationMemberships(organization);
        return Objects.nonNull(approvedOrganizationMemberShipList)
                ? approvedOrganizationMemberShipList
                .stream()
                .map(organizationMembership -> userService.findUser(organizationMembership.getMember().getId()))
                .collect(Collectors.toList()) : new ArrayList<>();
    }
}
