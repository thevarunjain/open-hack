package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String first_name, String last_name);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByScreenName(String screen_name);
}
