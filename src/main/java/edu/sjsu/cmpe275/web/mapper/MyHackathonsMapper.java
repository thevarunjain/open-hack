package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.response.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MyHackathonsMapper {
    public MyHackathonsResponseDto map(
            List<Hackathon> owner,
            List<Hackathon> judge,
            HashMap<Hackathon, Team> participantWithTeam
    ) {
        return MyHackathonsResponseDto.builder()
                .owner(mapHackathonsResponse(owner))
                .judge(mapHackathonsResponse(judge))
                .participant(mapHackathonsWithTeamResponse(participantWithTeam))
                .build();
    }

    private List<HackathonWithTeamResponseDto> mapHackathonsWithTeamResponse(
            HashMap<Hackathon, Team> participantWithTeam
    ) {
        List<HackathonWithTeamResponseDto> hackathonWithTeamResponseDtoList =
                new ArrayList<>();
        if (Objects.nonNull(participantWithTeam)) {
            Iterator<Map.Entry<Hackathon, Team>> itr = participantWithTeam.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Hackathon, Team> entry = itr.next();
                hackathonWithTeamResponseDtoList.add(
                        HackathonWithTeamResponseDto.builder()
                                .hackathon(map(entry.getKey()))
                                .team(mapTeamResponse(entry.getValue()))
                                .build()
                );
            }
        }
        return hackathonWithTeamResponseDtoList;
    }

    private TeamResponseDto mapTeamResponse(Team team){
        return TeamResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .isFinalized(team.getIsFinalized())
                .owner(mapOwnerResponse(team.getOwner()))
                .submissionURL(Objects.nonNull(team.getSubmissionURL())
                        ? team.getSubmissionURL()
                        : null)
                .grades(Objects.nonNull(team.getGrades())
                        ? team.getGrades()
                        : null)
                .build();
    }

    public HackathonResponseDto map(Hackathon hackathon){
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
                .build();
    }

    private AssociatedUserResponseDto mapOwnerResponse(final User user) {
        return AssociatedUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .screenName(user.getScreenName())
                .build();
    }


    private Set<AssociatedUserResponseDto> mapJudgeResponse (final Hackathon hackathon){
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

    public List<HackathonResponseDto> mapHackathonsResponse(final List<Hackathon> hackathons){
        List<HackathonResponseDto> hackathonResponseDtoList = new ArrayList<>();
        if (Objects.nonNull(hackathons)) {
            for(Hackathon hackathon : hackathons){
                hackathonResponseDtoList.add(
                        map(hackathon)
                );
            }
        }
        return hackathonResponseDtoList;
    }
}
