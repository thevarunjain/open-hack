package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.response.AssociatedMemberResponseDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TeamMembershipMapper {

    public TeamMembership map(final Team team, final User members, final String role) {
        return TeamMembership.builder()
                .id(mapMemberId(team, members))
                .teamId(team)
                .memberId(members)
                .role(role)
                .feePaid(false)
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

    public AssociatedMemberResponseDto map(final Long memberId, final String name,
                                           final String screenName, final String role,
                                           final Float amount, final Boolean feePaid) {
        return AssociatedMemberResponseDto.builder()
                .memberId(memberId)
                .firstName(name)
                .screenName(screenName)
                .role(role)
                .amount(Objects.nonNull(amount) ? amount : null)
                .feePaid(Objects.nonNull(feePaid) ? feePaid : null)
                .build();
    }


}
