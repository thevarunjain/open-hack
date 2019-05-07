package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByNameContainingIgnoreCase(String name);
}
