package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.repository.TeamMembershipRepository;
import edu.sjsu.cmpe275.web.model.response.AssociatedMemberResponseDto;
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


}


