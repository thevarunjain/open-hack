package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.security.CurrentUser;
import edu.sjsu.cmpe275.security.UserPrincipal;
import edu.sjsu.cmpe275.service.EmailService;
import edu.sjsu.cmpe275.service.OrganizationMembershipService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.mapper.MyHackathonsMapper;
import edu.sjsu.cmpe275.web.mapper.UserMapper;
import edu.sjsu.cmpe275.web.model.request.CreateUserRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateUserRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import edu.sjsu.cmpe275.web.model.response.MyHackathonsResponseDto;
import edu.sjsu.cmpe275.web.model.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final MyHackathonsMapper myHackathonsMapper;

    private final OrganizationService organizationService;

    private final OrganizationMembershipService organizationMembershipService;

    @Autowired
    public UserController(
            UserService userService,
            UserMapper userMapper,
            MyHackathonsMapper myHackathonsMapper,
            OrganizationService organizationService,
            OrganizationMembershipService organizationMembershipService
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.myHackathonsMapper = myHackathonsMapper;
        this.organizationService = organizationService;
        this.organizationMembershipService = organizationMembershipService;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getUsers(
            @CurrentUser UserPrincipal currentUser,
            @RequestParam(required = false) String name
    ) {
        System.out.println(currentUser.getUsername());
        List<User> allUsers = userService.findUsers(name);
        return userMapper.map(allUsers);
    }

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @Valid @RequestBody CreateUserRequestDto toCreate
    ) {
        // TODO Custom error on validation failure
        if (userService.existByEmail(toCreate.getEmail())) {
            throw new ConstraintViolationException("Email already taken", "email");
        }
        if (userService.existByScreenName(toCreate.getScreenName())) {
            throw new ConstraintViolationException("Screen name already taken", "screenName");
        }
        User createdUser  = userService.createUser(userMapper.map(toCreate), toCreate.getPassword());
        return userMapper.map(createdUser);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(@PathVariable @NotNull Long id) {
        User user  = userService.findUser(id);
        List<Organization> ownerOf = organizationService.findOrganizationsByOwner(user);
        OrganizationMembership membership =
                organizationMembershipService.findOrganizationByMemberAndStatus(
                        user,
                        "Approved"
                );
        Organization memberOf = Objects.nonNull(membership)
                ? membership.getOrganization() : null;
        return userMapper.map(
                user,
                ownerOf,
                memberOf
        );
    }
    @GetMapping(value = "/{id}/hackathons")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public MyHackathonsResponseDto getUserHackathons(@PathVariable @NotNull Long id) {
        // TODO should this be user from path or authenticated users
        User user  = userService.findUser(id);
        List<Hackathon> owner = userService.findHackathonsByOwner(user);
        List<Hackathon> judge = userService.findHackathonsByJudge(user);
        HashMap<Hackathon, Team> participantWithTeam = userService.findHackathonsByParticipant(user);
        return myHackathonsMapper.map(owner, judge, participantWithTeam);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody UpdateUserRequestDto fromRequest
    ) {
        User fromUpdate = userMapper.map(fromRequest);
        User updatedUser  = userService.updateUser(id, fromUpdate);
        return userMapper.map(updatedUser);
    }
}
