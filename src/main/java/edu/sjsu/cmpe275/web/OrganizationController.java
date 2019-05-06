package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.OrganizationMembershipService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.OrganizationMapper;
import edu.sjsu.cmpe275.web.mapper.OrganizationMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.CreateOrganizationRequestDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationResponseDto;
import edu.sjsu.cmpe275.web.model.response.SuccessResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    private final UserService userService;

    private final OrganizationMembershipService organizationMembershipService;

    private final OrganizationMapper organizationMapper;

    private final OrganizationMembershipMapper organizationMembershipMapper;
    @Autowired
    public OrganizationController(
            OrganizationService organizationService,
            UserService userService,
            OrganizationMembershipService organizationMembershipService,
            OrganizationMapper organizationMapper,
            OrganizationMembershipMapper organizationMembershipMapper
    ) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.organizationMembershipService = organizationMembershipService;
        this.organizationMapper = organizationMapper;
        this.organizationMembershipMapper = organizationMembershipMapper;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationResponseDto> getOrganizations() {
        List<Organization> allOrganizations  = organizationService.findOrganizations();
        return organizationMapper.map(allOrganizations);
    }

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrganizationResponseDto createOrganization(
            @Valid @RequestBody CreateOrganizationRequestDto toCreate,
            @NotNull @RequestParam Long ownerId, // TODO This should be from authentication token
            Errors validationErrors
    ) {
        // TODO Custom error on validation failure
        if (validationErrors.hasErrors()) {
        }
        Organization  createdOrganization = organizationService.createOrganization(
                organizationMapper.map(toCreate),
                ownerId
        );
        return organizationMapper.map(createdOrganization);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrganizationResponseDto getUser(@PathVariable @NotNull Long id) {
        Organization organization  = organizationService.findOrganization(id);
        return organizationMapper.map(organization);
    }

    @PostMapping(value = "/{id}/memberships")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseDto createOrganizationMembership(
            @PathVariable @NotNull Long id,
            @NotNull @RequestParam Long requesterId // TODO This should be from authentication token
    ) {
        User member = userService.findUser(requesterId);
        Organization organization = organizationService.findOrganization(id);
        organizationMembershipService.createOrganizationMembership(
                organizationMembershipMapper.map(organization, member)
        );
        return new SuccessResponseDto("Membership requested");
    }
}
