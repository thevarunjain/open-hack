package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationMembershipRepository
        extends JpaRepository<OrganizationMembership, OrganizationMembership.OrganizationMembershipId> {
    List<OrganizationMembership> findByOrganizationIdAndStatus(Long id, String status);

    List<OrganizationMembership> findByOrganizationId(Long id);
}
