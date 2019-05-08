package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.repository.TeamMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class TeamMembershipService {

    @Autowired
    private TeamMembershipRepository teamMembershipRepository;

    public TeamMembershipService(
            final TeamMembershipRepository teamMembershipRepository
    ) {
        this.teamMembershipRepository = teamMembershipRepository;
    }

    @Transactional
    public List<TeamMembership> findTeamMembers(final Team id){
        return teamMembershipRepository.findByTeamId(id);
    }

    public void createMembers(TeamMembership teamMember){
        teamMembershipRepository.save(teamMember);
    }


    public TeamMembership findByMemberAndTeam(User user, Team team) {
        return teamMembershipRepository.findByMemberIdAndTeamId(user, team)
                .orElse(null);
    }
}


