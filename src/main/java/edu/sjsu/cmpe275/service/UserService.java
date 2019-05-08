package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Role;
import edu.sjsu.cmpe275.domain.entity.RoleName;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.UserNotFoundException;
import edu.sjsu.cmpe275.domain.repository.RoleRepository;
import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.TeamNotFoundException;
import edu.sjsu.cmpe275.domain.exception.UserNotFoundException;
import edu.sjsu.cmpe275.domain.repository.HackathonRepository;
import edu.sjsu.cmpe275.domain.repository.TeamMembershipRepository;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.*;

@Component
public class UserService {

    private final UserRepository userRepository;

    private final HackathonRepository hackathonRepository;

    private final TeamMembershipRepository teamMembershipRepository;

    private TeamRepository teamRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(
            final UserRepository userRepository,
            final HackathonRepository hackathonRepository,
            final TeamMembershipRepository teamMembershipRepository,
            final TeamRepository teamRepository,
            final PasswordEncoder passwordEncoder,
            final RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.hackathonRepository = hackathonRepository;
        this.teamMembershipRepository = teamMembershipRepository;
        this.teamRepository = teamRepository;
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

    public boolean existByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existByScreenName(String screenName) {
        return userRepository.existsByScreenName(screenName);
    }

    public List<Hackathon> findHackathonsByOwner(final User user) {
        return hackathonRepository.findByOwnerId(user.getId())
                .orElse(null);
    }

    public List<Hackathon> findHackathonsByJudge(final User user) {
        // TODO Improve Performance
        List<Hackathon> hackathonsByJudge = new ArrayList<>();
        List<Hackathon> allHackathons = hackathonRepository.findAll();
        for (Hackathon hackathon: allHackathons) {
            Set<User> judges = hackathon.getJudges();
            for (User judge: judges) {
                if (Long.valueOf(judge.getId()).equals(user.getId())) {
                    hackathonsByJudge.add(hackathon);
                }
            }
        }
        return hackathonsByJudge;
    }

    public List<Hackathon> findHackathonsByParticipant(final User user) {
        // TODO Check what all hackathons that we need to send
        // TODO Improve Performance
        List<Hackathon> hackathonsByParticipant = new ArrayList<>();
        List<TeamMembership> memberships = teamMembershipRepository.findByMemberId(user.getId())
                .orElse(new ArrayList<>());
        for (TeamMembership teamMembership: memberships) {
            Team team = teamRepository.findById(teamMembership.getTeamId().getId())
                    .orElseThrow(() -> new TeamNotFoundException(teamMembership.getTeamId().getId()));
            hackathonsByParticipant.add(team.getHackathon());
        }
        return hackathonsByParticipant;
    }
}
