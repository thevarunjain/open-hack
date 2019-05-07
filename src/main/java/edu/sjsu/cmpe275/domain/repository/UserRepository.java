package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String first_name, String last_name);
}
