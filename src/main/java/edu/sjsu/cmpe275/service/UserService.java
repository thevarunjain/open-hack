package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.UserNotFoundException;
import edu.sjsu.cmpe275.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Component
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User createUser(final User user) {
        if (user.getEmail().endsWith("sjsu.edu"))
            user.setAdmin(true);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(final Long id, final User fromRequest) {
        User existingUser = findUser(id);
        existingUser.update(fromRequest);
        return existingUser;
    }
}
