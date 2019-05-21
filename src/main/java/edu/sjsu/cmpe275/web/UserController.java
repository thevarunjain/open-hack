package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.security.CurrentUser;
import edu.sjsu.cmpe275.security.UserPrincipal;
import edu.sjsu.cmpe275.service.AmazonService;
import edu.sjsu.cmpe275.service.OrganizationMembershipService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.MyHackathonsMapper;
import edu.sjsu.cmpe275.web.mapper.UserMapper;
import edu.sjsu.cmpe275.web.model.request.CreateUserRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateUserRequestDto;
import edu.sjsu.cmpe275.web.model.response.MyHackathonsResponseDto;
import edu.sjsu.cmpe275.web.model.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final AmazonService amazonService;

    @Autowired
    public UserController(
            UserService userService,
            UserMapper userMapper,
            MyHackathonsMapper myHackathonsMapper,
            OrganizationService organizationService,
            OrganizationMembershipService organizationMembershipService,
            AmazonService amazonService
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.myHackathonsMapper = myHackathonsMapper;
        this.organizationService = organizationService;
        this.organizationMembershipService = organizationMembershipService;
        this.amazonService = amazonService;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getUsers(
            @CurrentUser UserPrincipal currentUser,
            @RequestParam(required = false) String name
    ) {
        List<User> allUsers = userService.findUsers(name);
        return userMapper.map(allUsers);
    }

    @GetMapping(value = "/checkEmail")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void checkEmail(
            @RequestParam String email
    ) {
        if (userService.existByEmail(email)) {
            throw new ConstraintViolationException("Email already taken", "email");
        }
    }

    @GetMapping(value = "/checkScreenName")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void checkScreenName(
            @RequestParam String screenName
    ) {
        if (userService.existByScreenName(screenName)) {
            throw new ConstraintViolationException("Screen name already taken", "screenName");
        }
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
        User createdUser = userService.createUser(userMapper.map(toCreate), toCreate.getPassword());
        return userMapper.map(createdUser);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(
            @PathVariable @NotNull Long id
    ) {
        User user = userService.findUser(id);
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
    public MyHackathonsResponseDto getUserHackathons(
            @PathVariable @NotNull Long id
    ) {
        User user = userService.findUser(id);
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
        // TODO only current logged in user can update his/her profile
        User fromUpdate = userMapper.map(fromRequest);
        User updatedUser = userService.updateUser(id, fromUpdate);
        return userMapper.map(updatedUser);
    }

    @PostMapping("/{id}/upload")
    public String uploadFile(
            @PathVariable @NotNull Long id,
            @RequestPart(value = "file") MultipartFile file
    ) {
        return amazonService.uploadFile(file);
    }
}
