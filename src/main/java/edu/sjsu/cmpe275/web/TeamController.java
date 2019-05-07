package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.service.TeamMembershipService;
import edu.sjsu.cmpe275.service.TeamService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.TeamMapper;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.TeamResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/hackathons")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;
    private final TeamMembershipService teamMembershipService;
    private final TeamMapper teamMapper;
    private final TeamMembershipMapper teamMembershipMapper;

    @Autowired
    public TeamController(
            TeamService teamService,
            UserService userService,
            TeamMembershipService teamMembershipService,
            TeamMapper teamMapper,
            TeamMembershipMapper teamMembershipMapper
    ) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamMembershipService = teamMembershipService;
        this.teamMapper = teamMapper;
        this.teamMembershipMapper = teamMembershipMapper;
    }

    @PostMapping(value = "/{id}/teams", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto createTeam(
            @Valid @RequestBody CreateTeamRequestDto toCreateTeam,
            @PathVariable @NotNull Long id,
            Errors validationErrors
    ) {
        // TODO Custom error on validation failure
        if (validationErrors.hasErrors()) {
        }

        Team createdTeam = teamService.createTeam(
                teamMapper.map(toCreateTeam),
                toCreateTeam.getMembers(),
                toCreateTeam.getRoles(),
                id
        );

        return teamMapper.map(createdTeam);
    }

}
