package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.security.CurrentUser;
import edu.sjsu.cmpe275.security.UserPrincipal;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.TeamMembershipService;
import edu.sjsu.cmpe275.service.TeamService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.PaymentMapper;
import edu.sjsu.cmpe275.web.mapper.TeamMapper;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedMemberResponseDto;
import edu.sjsu.cmpe275.web.model.response.PaymentResponseDto;
import edu.sjsu.cmpe275.web.model.response.SuccessResponseDto;
import edu.sjsu.cmpe275.web.model.response.TeamResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hackathons")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamMembershipService teamMembershipService;
    private final TeamMapper teamMapper;
    private final TeamMembershipMapper teamMembershipMapper;
    private final HackathonService hackathonService;
    private final PaymentMapper paymentMapper;

    @Autowired
    public TeamController(
            TeamService teamService,
            UserService userService,
            TeamMembershipService teamMembershipService,
            TeamMapper teamMapper,
            TeamMembershipMapper teamMembershipMapper,
            HackathonService hackathonService,
            PaymentMapper paymentMapper
    ) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamMembershipService = teamMembershipService;
        this.teamMapper = teamMapper;
        this.teamMembershipMapper = teamMembershipMapper;
        this.hackathonService = hackathonService;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping(value = "/{hid}/teams")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponseDto> getTeamsForHackathon(
            @PathVariable @NotNull Long hid
    ) {
        List<Team> allTeams = teamService.findallTeamsForHackathon(hid);
        return teamMapper.map(allTeams);
    }

    @GetMapping(value = "/{hid}/teams/{tid}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto getTeam(
            @PathVariable @NotNull Long hid,
            @PathVariable @NotNull Long tid
    ) {
        Hackathon hackathon = hackathonService.findHackathon(hid);
        List<Team> getTeam = teamService.findTeamForHackathon(hackathon, tid);
        Team team = getTeam.get(0);
        List<TeamMembership> allMembers = teamMembershipService.findTeamMembers(team);
        List<AssociatedMemberResponseDto> memberResponse = new ArrayList<>();
        for (TeamMembership teamMembership : allMembers) {
            memberResponse.add(
                    teamMembershipMapper.map(
                            teamMembership.getId().getMemberId(),
                            teamMembership.getMemberId().getFirstName(),
                            teamMembership.getMemberId().getScreenName(),
                            teamMembership.getRole(),
                            teamMembership.getAmount(),
                            teamMembership.getFeePaid()
                    )
            );
        }
        return teamMapper.map(team, memberResponse);
    }

    @PostMapping(value = "/{id}/teams")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto createTeam(
            @Valid @RequestBody CreateTeamRequestDto toCreateTeam,
            @PathVariable @NotNull Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        User owner = userService.findUser(currentUser.getId());
        Hackathon hackathon = hackathonService.findHackathon(id);
        Team createdTeam = teamService.createTeam(
                teamMapper.map(toCreateTeam, owner, hackathon),
                toCreateTeam.getMembers(),
                toCreateTeam.getRoles(),
                owner
        );
        return teamMapper.map(createdTeam);
    }

    @PatchMapping(value = "/{hid}/teams/{tid}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDto updateHackathon(
            @Valid @RequestBody UpdateTeamRequestDto upadateTeam,
            @NonNull @PathVariable Long hid,
            @NonNull @PathVariable Long tid
    ) {
        Team team = teamService.updateTeam(hid, upadateTeam, tid);
        return teamMapper.map(team);
    }

    @GetMapping(value = "/{hid}/teams/{tid}/payment")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponseDto getPayment(
            @PathVariable @NotNull Long hid,
            @PathVariable @NotNull Long tid,
            @RequestParam @NotNull Long participantId
    ) {
        User user = userService.findUser(participantId);
        Team team = teamService.findTeam(tid);
        Hackathon hackathon = hackathonService.findHackathon(hid);
        Float amount = teamService.getPaymentForMember(
                user,
                team,
                hackathon
        );
        return paymentMapper.map(amount, hackathon.getName());
    }

    @PostMapping(value = "/{hid}/teams/{tid}/payment")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseDto processPayment(
            @PathVariable @NotNull Long hid,
            @PathVariable @NotNull Long tid,
            @RequestParam @NotNull Float amount,
            @RequestParam @NotNull Long participantId
    ) {
        User user = userService.findUser(participantId);
        Team team = teamService.findTeam(tid);
        teamService.processPaymemtForMember(
                user,
                team,
                amount
        );
        return new SuccessResponseDto("Payment Processed");
    }
}
