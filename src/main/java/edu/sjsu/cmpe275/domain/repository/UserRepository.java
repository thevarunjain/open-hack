package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
