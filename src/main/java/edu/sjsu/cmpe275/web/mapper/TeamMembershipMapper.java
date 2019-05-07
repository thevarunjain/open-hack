package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.response.AssociatedMemberResponseDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedSponsorResponseDto;
import org.springframework.stereotype.Component;

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

    public AssociatedMemberResponseDto map(final Long memberId, final String name,
                                           final String screenName,final String role,
                                           final Float amount, final Boolean fee_paid){
        return AssociatedMemberResponseDto.builder()
                .memberId(memberId)
                .firstName(name)
                .screenName(screenName)
                .role(role)
                .amount(amount)
                .fee_paid(fee_paid)
                .role(role)
                .build();
    }



}
