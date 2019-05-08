package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.domain.exception.TeamNotFoundException;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateTeamRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Component
public class TeamService {

    private TeamRepository teamRepository;
    private TeamMembershipMapper teamMembershipMapper;
    private UserService userService;
    private HackathonService hackathonService;
    private TeamMembershipService teamMembershipService;
    private HackathonSponsorService hackathonSponsorService;
    private OrganizationMembershipService organizationMembershipService;


    @Autowired
    public TeamService(
            final TeamRepository teamRepository,
            final TeamMembershipMapper teamMembershipMapper,
            final UserService userService,
            final HackathonService hackathonService,
            final TeamMembershipService teamMembershipService,
            final HackathonSponsorService hackathonSponsorService,
            final OrganizationMembershipService organizationMembershipService
    ) {
        this.teamRepository = teamRepository;
        this.teamMembershipMapper = teamMembershipMapper;
        this.teamMembershipService = teamMembershipService;
        this.userService = userService;
        this.hackathonService = hackathonService;
        this.hackathonSponsorService = hackathonSponsorService;
        this.organizationMembershipService = organizationMembershipService;
    }

    public List<Team> findallTeamsForHackathon(final long id){

            Hackathon hackathon = hackathonService.findHackathon(id);
            List<Team> allTeams = teamRepository.findByHackathon(hackathon);

            return allTeams;

    }

    public List<Team> findTeamForHackathon(Hackathon hid, Long tid) {
            return teamRepository.findByHackathonAndId(hid, tid);
    }

    public List<Team> findTeams(){
        return teamRepository.findAll();
    }

    public Team findTeam(final Long id){
        return teamRepository.findById(id)
                .orElseThrow(()-> new TeamNotFoundException(id));
    }

    @Transactional
    public Team createTeam(final Team team, final List<Long> members, final List<String> roles){

        Team createdTeam = teamRepository.save(team);

        for(int i=0;i<members.size();i++){
            TeamMembership createMember = teamMembershipMapper.map(
                    findTeam(createdTeam.getId()),
                    userService.findUser(members.get(i)),
                    roles.get(i));

            teamMembershipService.createMembers(createMember);
        }

        return createdTeam;
    }

    @Transactional
    public Team updateTeam(Long hid, UpdateTeamRequestDto upadateTeam, Long tid) {

        Hackathon hackathon = hackathonService.findHackathon(hid);
              List<Team> getTeams =findTeamForHackathon(hackathon, tid);
                    Team team = getTeams.get(0);

                    team.setGrades(Objects.nonNull(upadateTeam.getGrades())
                                          ? upadateTeam.getGrades()
                                          : team.getGrades() );

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
            HackathonSponsor hackathonSponsor =  hackathonSponsorService.findHackathonSponsor(hackathon, memberOf);
            Float discount = 0F;
            if (Objects.nonNull(hackathonSponsor)) {
                discount = hackathonSponsor.getDiscount() * hackathonFee;
            }
            return hackathonFee - discount;
        } else {
            return hackathonFee;
        }
    }
}
