package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Role;
import edu.sjsu.cmpe275.domain.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
