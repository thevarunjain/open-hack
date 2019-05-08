package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.TeamNotFoundException;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateTeamRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class TeamService {

    private TeamRepository teamRepository;
    private TeamMembershipMapper teamMembershipMapper;
    private UserService userService;
    private HackathonService hackathonService;
    private TeamMembershipService teamMembershipService;
    private HackathonSponsorService hackathonSponsorService;
    private final EmailService emailService;

    @Autowired
    public TeamService(
            final TeamRepository teamRepository,
            final TeamMembershipMapper teamMembershipMapper,
            final UserService userService,
            final HackathonService hackathonService,
            final TeamMembershipService teamMembershipService,
            final HackathonSponsorService hackathonSponsorService,
            final EmailService emailService
    ) {
        this.teamRepository = teamRepository;
        this.teamMembershipMapper = teamMembershipMapper;
        this.teamMembershipService = teamMembershipService;
        this.userService = userService;
        this.hackathonService = hackathonService;
        this.hackathonSponsorService = hackathonSponsorService;
        this.emailService = emailService;
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

    public Team findTeam(final long id){
        return teamRepository.findById(id)
                .orElseThrow(()-> new TeamNotFoundException(id));
    }

    @Transactional
    public Team createTeam(final Team team, List<Long> members, final List<String> roles, User owner){

        Team createdTeam = teamRepository.save(team);

                members.add(owner.getId());
        for(int i=0;i<members.size();i++){

            TeamMembership createMember;

            if(members.get(i)==owner.getId()){
                createMember = teamMembershipMapper.map(
                        findTeam(createdTeam.getId()),
                        userService.findUser(members.get(i)),
                        "Team Lead");
            }else{
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

        String subject = "Open-Hack 2019 Invitation to Team - "+ createdTeam.getName();
        Long hid = createdTeam.getHackathon().getId();
        Long tid = createdTeam.getId();
        String localServerUrl = "http://localhost:3000";
        String hostedServerUrl = "" ;
        String message = " Hi,\n" +
                "Welcome to Open Hackathon 2019\n" +
                "You are invited to join our hackathon team : "+ createdTeam.getName() + "\n" +
                "Proceed to pay on "+localServerUrl+"/hackathons/"+hid+"/teams/"+tid+"/payments" +
                "\n" +
                "\n" +
                "Thank You\n" +
                createdTeam.getOwner().getFirstName() +" "+ createdTeam.getOwner().getLastName();

        List<String> allEmails= new ArrayList<>();

        for(Long mid : members){
            String email = userService.findUser(mid).getEmail();
                allEmails.add(email);
        }

        for(String email : allEmails){
            System.err.println("Sending mail to "+ email);
            emailService.sendSimpleMessage(email,subject, message);
        }


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
}
