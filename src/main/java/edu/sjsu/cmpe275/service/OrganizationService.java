package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.OrganizationNotFoundException;
import edu.sjsu.cmpe275.domain.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class OrganizationService {
    private OrganizationRepository organizationRepository;

    private UserService userService;

    @Autowired
    public OrganizationService(
            final OrganizationRepository organizationRepository,
            final UserService userService
    ) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
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
}
