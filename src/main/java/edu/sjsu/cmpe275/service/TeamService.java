package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.exception.TeamNotFoundException;
import edu.sjsu.cmpe275.domain.repository.TeamMembershipRepository;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.TeamMembershipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class TeamService {

    private TeamRepository teamRepository;
    private TeamMembershipMapper teamMembershipMapper;
    private UserService userService;
    private HackathonService hackathonService;
    private TeamMembershipService teamMembershipService;

    @Autowired
    public TeamService(
            final TeamRepository teamRepository,
            final TeamMembershipMapper teamMembershipMapper,
            final UserService userService,
            final HackathonService hackathonService,
            final TeamMembershipService teamMembershipService
    ) {
        this.teamRepository = teamRepository;
        this.teamMembershipMapper = teamMembershipMapper;
        this.teamMembershipService = teamMembershipService;
        this.userService = userService;
        this.hackathonService = hackathonService;
    }

    public Team findTeam(final long id){
        return teamRepository.findById(id)
                .orElseThrow(()-> new TeamNotFoundException(id));
    }

    @Transactional
    public Team createTeam(final Team team, final List<Long> members, final List<String> roles, final Long hackathonId){

        Hackathon checkHackathonId = hackathonService.findHackathon(hackathonId);
        team.setHackathon(checkHackathonId);
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
}
