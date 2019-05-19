package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeamMapper {

    public Team map(CreateTeamRequestDto toCreateTeam, User owner, Hackathon hackathon) {
        return Team.builder()
                .name(toCreateTeam.getName())
                .isFinalized(false)
                .owner(owner)
                .hackathon(hackathon)
                .submissionURL(Objects.nonNull(toCreateTeam.getSubmissionURL())
                        ? toCreateTeam.getSubmissionURL()
                        : null)
                .grades(Objects.nonNull(toCreateTeam.getGrades())
                        ? toCreateTeam.getGrades()
                        : null)
                .build();
    }

    public TeamResponseDto map(Team team) {
        return TeamResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .isFinalized(team.getIsFinalized())
                .owner(mapOwnerResponse(team.getOwner()))
                .hackathon(mapHackathonResponse(team.getHackathon()))
                .submissionURL(Objects.nonNull(team.getSubmissionURL())
                        ? team.getSubmissionURL()
                        : null)
                .grades(Objects.nonNull(team.getGrades())
                        ? team.getGrades()
                        : null)
                .build();
    }

    public List<TeamResponseDto> map(final List<Team> teams) {
        List<TeamResponseDto> teamResponseDtoList = new ArrayList<>();
        for (Team team : teams) {
            teamResponseDtoList.add(
                    map(team)
            );
        }
        return teamResponseDtoList;
    }


    private AssociatedUserResponseDto mapOwnerResponse(final User user) {
        return AssociatedUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .screenName(user.getScreenName())
                .build();
    }


    public HackathonResponseDto mapHackathonResponse(Hackathon hackathon) {
        return HackathonResponseDto.builder()
                .id(hackathon.getId())
                .name(hackathon.getName())
                .description(hackathon.getDescription())
//                .startDate(hackathon.getStartDate())
//                .endDate(hackathon.getEndDate())
                .maxSize(hackathon.getMaxSize())
                .minSize(hackathon.getMinSize())
//                .fee(hackathon.getFee())
//                .judges(mapJudgeResponse(hackathon))
//                .status(hackathon.getStatus())
//                .owner(mapOwnerResponse(hackathon.getOwner()))
                .build();
    }

    private Set<AssociatedUserResponseDto> mapJudgeResponse(final Hackathon hackathon) {
        Set<AssociatedUserResponseDto> members = Objects.nonNull(hackathon.getJudges())
                ? hackathon.getJudges()
                .stream()
                .map(judge -> AssociatedUserResponseDto.builder()
                        .id(judge.getId())
                        .email(judge.getEmail())
                        .screenName(judge.getScreenName())
                        .build()
                )
                .collect(Collectors.toSet())
                : new HashSet<>();

        return members;
    }

    public TeamResponseDto map(Team team, List<AssociatedMemberResponseDto> memberResponse) {
        return TeamResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .hackathon(mapHackathonResponse(team.getHackathon()))
                .owner(mapOwnerResponse(team.getOwner()))
                .members(memberResponse)
                .grades(team.getGrades())
                .submissionURL(team.getSubmissionURL())
                .build();
    }


    public HackathonResponseDto map(Hackathon hackathon, List<AssociatedSponsorResponseDto> sponsorResponse) {
        return HackathonResponseDto.builder()
                .id(hackathon.getId())
                .name(hackathon.getName())
                .description(hackathon.getDescription())
                .startDate(hackathon.getStartDate())
                .endDate(hackathon.getEndDate())
                .maxSize(hackathon.getMaxSize())
                .minSize(hackathon.getMinSize())
                .fee(hackathon.getFee())
                .judges(mapJudgeResponse(hackathon))
                .status(hackathon.getStatus())
                .owner(mapOwnerResponse(hackathon.getOwner()))
                .sponsors(sponsorResponse)
                .build();
    }
}


