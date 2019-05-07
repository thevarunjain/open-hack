package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedUserResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TeamMembershipMapper {

    public TeamMembership map(final Team team, final User members, final String role){
        return TeamMembership.builder()
                .id(mapMemberId(team, members))
                .teamId(team)
                .memberId(members)
                .role(role)
                .build();
    }

    private TeamMembership.TeamMembershipId mapMemberId(
            final Team team,
            final User members
    ) {
        return TeamMembership.TeamMembershipId.builder()
                .teamId(team.getId())
                .memberId(members.getId())
                .build();
    }
}
