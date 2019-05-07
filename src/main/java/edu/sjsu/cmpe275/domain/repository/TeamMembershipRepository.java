package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMembershipRepository
        extends JpaRepository<TeamMembership, TeamMembership.TeamMembershipId> {

    List<TeamMembership> findByTeamId(Team id);
}
