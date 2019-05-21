package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.domain.exception.TeamNotFoundException;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateTeamRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMembershipMapper teamMembershipMapper;
    private final UserService userService;
    private final HackathonService hackathonService;
    private final TeamMembershipService teamMembershipService;
    private final HackathonSponsorService hackathonSponsorService;
    private final OrganizationMembershipService organizationMembershipService;
    private final EmailService emailService;

    @Autowired
    public TeamService(
            final TeamRepository teamRepository,
            final TeamMembershipMapper teamMembershipMapper,
            final UserService userService,
            final HackathonService hackathonService,
            final TeamMembershipService teamMembershipService,
            final HackathonSponsorService hackathonSponsorService,
            final OrganizationMembershipService organizationMembershipService,
            final EmailService emailService
    ) {
        this.teamRepository = teamRepository;
        this.teamMembershipMapper = teamMembershipMapper;
        this.teamMembershipService = teamMembershipService;
        this.userService = userService;
        this.hackathonService = hackathonService;
        this.hackathonSponsorService = hackathonSponsorService;
        this.organizationMembershipService = organizationMembershipService;
        this.emailService = emailService;
    }

    public List<Team> findallTeamsForHackathon(final long id) {

        Hackathon hackathon = hackathonService.findHackathon(id);
        List<Team> allTeams = teamRepository.findByHackathon(hackathon);

        return allTeams;

    }

    public List<Team> findTeamForHackathon(Hackathon hid, Long tid) {
        return teamRepository.findByHackathonAndId(hid, tid);
    }

    public List<Team> findTeams() {
        return teamRepository.findAll();
    }

    public Team findTeam(final Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Transactional
    public Team createTeam(final Team team, List<Long> members, final List<String> roles, User owner) {

        Team createdTeam = teamRepository.save(team);

        members.add(owner.getId());
        for (int i = 0; i < members.size(); i++) {

            TeamMembership createMember;

            if (members.get(i) == owner.getId()) {
                createMember = teamMembershipMapper.map(
                        findTeam(createdTeam.getId()),
                        userService.findUser(members.get(i)),
                        "Team Lead");
            } else {
                createMember = teamMembershipMapper.map(
                        findTeam(createdTeam.getId()),
                        userService.findUser(members.get(i)),
                        roles.get(i));
            }

            sendEmailToAllTeamMembers(createdTeam, members);

            teamMembershipService.createMembers(createMember);

        }
        return createdTeam;
    }

    private void sendEmailToAllTeamMembers(Team createdTeam, List<Long> members) {

        String subject = "Open-Hack 2019 Invitation to Team - " + createdTeam.getName();
        Long hid = createdTeam.getHackathon().getId();
        Long tid = createdTeam.getId();
        String localServerUrl = "http://localhost:3000";
        String hostedServerUrl = "https://openhacks.herokuapp.com";

        for (Long mid : members) {
            String message = " Hi,\n" +
                    "Welcome to Open Hackathon 2019\n" +
                    "You are invited to join our hackathon team : " + createdTeam.getName() + "\n" +
                    "Proceed to pay on " + hostedServerUrl + "/payments/" + hid + "/" + tid + "/" + mid +
                    "\n" +
                    "\n" +
                    "Thank You\n" +
                    createdTeam.getOwner().getFirstName() + " " + createdTeam.getOwner().getLastName();
            String email = userService.findUser(mid).getEmail();
            emailService.sendSimpleMessage(email, subject, message);
        }
    }

    @Transactional
    public Team updateTeam(Long hid, UpdateTeamRequestDto upadateTeam, Long tid) {

        Hackathon hackathon = hackathonService.findHackathon(hid);
        List<Team> getTeams = findTeamForHackathon(hackathon, tid);
        Team team = getTeams.get(0);

        team.setGrades(Objects.nonNull(upadateTeam.getGrades())
                ? upadateTeam.getGrades()
                : team.getGrades());

        team.setIsFinalized(Objects.nonNull(upadateTeam.getIsFinalized())
                ? upadateTeam.getIsFinalized()
                : team.getIsFinalized());

        team.setSubmissionURL(Objects.nonNull(upadateTeam.getSubmissionURL())
                ? upadateTeam.getSubmissionURL()
                : team.getSubmissionURL());
        return team;
    }

    public Float getPaymentForMember(
            final User user,
            final Team team,
            final Hackathon hackathon
    ) {
        OrganizationMembership membership =
                organizationMembershipService.findOrganizationByMemberAndStatus(
                        user,
                        "Approved"
                );
        Organization memberOf = Objects.nonNull(membership)
                ? membership.getOrganization() : null;
        Float hackathonFee = hackathon.getFee();
        if (Objects.nonNull(memberOf)) {
            HackathonSponsor hackathonSponsor = hackathonSponsorService.findHackathonSponsor(hackathon, memberOf);
            Integer discount = 0;
            if (Objects.nonNull(hackathonSponsor)) {
                discount = hackathonSponsor.getDiscount();
            }
            return hackathonFee - (hackathonFee * discount) / 100;
        } else {
            return hackathonFee;
        }
    }

    @Transactional
    public void processPaymemtForMember(
            final User user,
            final Team team,
            final Float amount
    ) {
        TeamMembership teamMembership = teamMembershipService.findByMemberAndTeam(user, team);
        teamMembership.setAmount(amount);
        teamMembership.setFeePaid(true);
        // Mark the team finalized if everyone paid fees
        boolean isPaidByEveryone = true;
        List<TeamMembership> teamMembershipList =
                teamMembershipService.findTeamMembers(teamMembership.getTeamId());
        for (TeamMembership membership : teamMembershipList) {
            if (!membership.getFeePaid()) {
                isPaidByEveryone = false;
                break;
            }
        }
        if (isPaidByEveryone) {
            Team toUpdate = teamRepository.findById(teamMembership.getTeamId().getId())
                    .orElse(null);
            if (Objects.nonNull(toUpdate)) {
                toUpdate.setIsFinalized(true);
            }

        }
    }
}
