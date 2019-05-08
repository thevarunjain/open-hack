package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Role;
import edu.sjsu.cmpe275.domain.entity.RoleName;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.RoleNotFoundException;
import edu.sjsu.cmpe275.domain.exception.UserNotFoundException;
import edu.sjsu.cmpe275.domain.repository.RoleRepository;
import edu.sjsu.cmpe275.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UserService {

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<User> findUsers(final String name) {
        // TODO Only return users with valid email address
        if (Objects.nonNull(name))
            return userRepository.findByFirstNameContainingOrLastNameContainingAllIgnoreCase(name, name);
        else
            return userRepository.findAll();
    }

    public User findUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User createUser(final User user, final String password) {
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException(RoleName.ROLE_ADMIN.name()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException(RoleName.ROLE_USER.name()));

        if (user.getEmail().endsWith("sjsu.edu")) {
            user.setAdmin(true);
            user.setRoles(Collections.singleton(adminRole));
        } else {
            user.setRoles(Collections.singleton(userRole));
        }
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(final Long id, final User fromRequest) {
        User existingUser = findUser(id);
        existingUser.update(fromRequest);
        return existingUser;
    }

    public boolean existByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existByScreenName(String screenName) {
        return userRepository.existsByScreenName(screenName);
    }
}
