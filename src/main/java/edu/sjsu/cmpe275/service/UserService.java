package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.UserNotFoundException;
import edu.sjsu.cmpe275.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {
        // TODO Only return users with valid email address
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
}
