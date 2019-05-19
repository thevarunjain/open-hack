package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.security.CurrentUser;
import edu.sjsu.cmpe275.security.UserPrincipal;
import edu.sjsu.cmpe275.service.OrganizationMembershipService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.OrganizationMapper;
import edu.sjsu.cmpe275.web.mapper.OrganizationMembershipMapper;
import edu.sjsu.cmpe275.web.mapper.UserMapper;
import edu.sjsu.cmpe275.web.model.request.CreateOrganizationRequestDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationMembershipResponseDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationResponseDto;
import edu.sjsu.cmpe275.web.model.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    private final UserService userService;

    private final OrganizationMembershipService organizationMembershipService;

    private final OrganizationMapper organizationMapper;

    private final OrganizationMembershipMapper organizationMembershipMapper;

    private final UserMapper userMapper;
    @Autowired
    public OrganizationController(
            OrganizationService organizationService,
            UserService userService,
            OrganizationMembershipService organizationMembershipService,
            OrganizationMapper organizationMapper,
            OrganizationMembershipMapper organizationMembershipMapper,
            UserMapper userMapper
    ) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.organizationMembershipService = organizationMembershipService;
        this.organizationMapper = organizationMapper;
        this.organizationMembershipMapper = organizationMembershipMapper;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationResponseDto> getOrganizations(
            @RequestParam(required = false) String name
    ) {
        List<Organization> allOrganizations = organizationService.findOrganizations(name);
        return organizationMapper.map(allOrganizations);
    }

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationResponseDto createOrganization(
            @Valid @RequestBody CreateOrganizationRequestDto toCreate,
            @CurrentUser UserPrincipal currentUser
    ) {
        User user = userService.findUser(currentUser.getId());
        Organization  createdOrganization = organizationService.createOrganization(
                organizationMapper.map(toCreate),
                user.getId()
        );
        return organizationMapper.map(createdOrganization);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrganizationResponseDto getOrganization(
            @PathVariable @NotNull Long id
    ) {
        Organization organization  = organizationService.findOrganization(id);
        return organizationMapper.map(organization);
    }

    @PostMapping(value = "/{id}/memberships")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationMembershipResponseDto createOrganizationMembership(
            @PathVariable @NotNull Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        User user = userService.findUser(currentUser.getId());
        Organization organization = organizationService.findOrganization(id);
        User member = userService.findUser(user.getId());

        OrganizationMembership createdOrganizationMembership  =
                organizationMembershipService.createOrganizationMembership(
                        organizationMembershipMapper.map(organization, member),
                        organizationService.findOrganization(id).getOwner().getEmail()
                );
        return organizationMembershipMapper.map(createdOrganizationMembership);
    }

    @GetMapping(value = "/{id}/memberships")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationMembershipResponseDto> getOrganizationMemberships(
            @PathVariable @NotNull Long id,
            @RequestParam(required = false) String status
    ) {
        Organization organization = organizationService.findOrganization(id);
        List<OrganizationMembership> organizationMembershipList =
                organizationMembershipService.findOrganizationMemberships(organization, status);
        return organizationMembershipMapper.map(organizationMembershipList);
    }

    @PutMapping(value = "/{id}/memberships")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrganizationMembershipResponseDto updateOrganizationMembership(
            @PathVariable @NotNull Long id,
            @NotNull @RequestParam Long requesterId, // TODO This should be from authentication token
            @RequestParam Long memberId,
            @NotNull @RequestParam String toState
    ) {
        Organization organization = organizationService.findOrganization(id);
        User requester = userService.findUser(requesterId);
        User member = Objects.nonNull(memberId) ? userService.findUser(memberId): null;
        OrganizationMembership updatedOrganizationMembership  =
                organizationMembershipService.updateOrganizationMembership(
                        organization,
                        Objects.nonNull(member) ? member : requester,
                        toState
                );
        return organizationMembershipMapper.map(updatedOrganizationMembership);
    }

    @GetMapping(value = "/{id}/members")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getApprovedOrganizationMembers(@PathVariable @NotNull Long id) {
        // TODO changes in mapper so as to send AssociatedUserResponseDto
        List<User> orgMembers  = organizationService.findApprovedOrganizationMembers(id);
        return userMapper.map(orgMembers);
    }
}
