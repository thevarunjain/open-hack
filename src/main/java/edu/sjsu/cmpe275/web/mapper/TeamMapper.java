package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.web.model.request.CreateTeamRequestDto;
import edu.sjsu.cmpe275.web.model.response.TeamResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamMapper {

    public Team map(CreateTeamRequestDto toCreateTeam){
        return Team.builder()
                .name(toCreateTeam.getName())
                .build();
    }


    public TeamResponseDto map(Team team){
        return TeamResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public List<TeamResponseDto> map(final List<Team> teams){
        List<TeamResponseDto> teamResponseDtoList = new ArrayList<>();
        for(Team team : teams){
            teamResponseDtoList.add(
                    map(team)
            );
        }
        return teamResponseDtoList;
    }
}
