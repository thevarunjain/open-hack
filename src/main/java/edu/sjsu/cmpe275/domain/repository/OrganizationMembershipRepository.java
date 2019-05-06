package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMembershipRepository
        extends JpaRepository<OrganizationMembership, OrganizationMembership.OrganizationMembershipId> {
}