package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.TeamMembershipService;
import edu.sjsu.cmpe275.service.TeamService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.TeamMapper;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedMemberResponseDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedSponsorResponseDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import edu.sjsu.cmpe275.web.model.response.TeamResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hackathons")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;
    private final TeamMembershipService teamMembershipService;
    private final TeamMapper teamMapper;
    private final TeamMembershipMapper teamMembershipMapper;
    private final HackathonService hackathonService;

    @Autowired
    public TeamController(
            TeamService teamService,
            UserService userService,
            TeamMembershipService teamMembershipService,
            TeamMapper teamMapper,
            TeamMembershipMapper teamMembershipMapper,
            HackathonService hackathonService
    ) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamMembershipService = teamMembershipService;
        this.teamMapper = teamMapper;
        this.teamMembershipMapper = teamMembershipMapper;
        this.hackathonService = hackathonService;
    }

    @GetMapping(value = "/{hid}/teams", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponseDto> getallTeamsForHackathon(@RequestParam(required = false) String name,
                                                    @PathVariable @NotNull Long hid){

        List<Team> allTeams = teamService.findallTeamsForHackathon(hid);
            return teamMapper.map(allTeams);
    }

    @GetMapping(value = "/{hid}/teams/{tid}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto getTeams(@RequestParam(required = false) String name,
                                          @PathVariable @NotNull Long hid,
                                          @PathVariable @NotNull Long tid){

        Hackathon hackathon = hackathonService.findHackathon(hid);
            List<Team> getTeam = teamService.findTeamForHackathon(hackathon, tid);
                    Team team = getTeam.get(0);

        List<TeamMembership> allMembers  =teamMembershipService.findTeamMembers(team);

        List<AssociatedMemberResponseDto> memberResponse = new ArrayList<>();


        for(TeamMembership teamMembership : allMembers){
            memberResponse.add(
                    teamMembershipMapper.map(
                            teamMembership.getId().getMemberId(),
                            teamMembership.getMemberId().getFirstName(),
                            teamMembership.getMemberId().getScreenName(),
                            teamMembership.getRole(),
                            teamMembership.getAmount(),
                            teamMembership.getFee_paid()
                    )
            );
        }
        return teamMapper.map(team, memberResponse);
    }


    @PostMapping(value = "/{id}/teams", produces = "application/json" )
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto createTeam(
            @Valid @RequestBody CreateTeamRequestDto toCreateTeam,
            @PathVariable @NotNull Long id,
            @NotNull @RequestParam Long ownerId,
            Errors validationErrors
    ) {
        // TODO Custom error on validation failure
        if (validationErrors.hasErrors()) {
        }

        User owner = userService.findUser(ownerId);
        Hackathon hackathon =  hackathonService.findHackathon(id);

        Team createdTeam = teamService.createTeam(
                teamMapper.map(toCreateTeam,owner, hackathon),
                toCreateTeam.getMembers(),
                toCreateTeam.getRoles()
        );

        return teamMapper.map(createdTeam);
    }

}
