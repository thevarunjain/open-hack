package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class HackathonMapper {

        public Hackathon map(CreateHackathonRequestDto toCreateHackathon, Set<User> judges){
            return Hackathon.builder()
                    .name(toCreateHackathon.getName())
                    .description(toCreateHackathon.getDescription())
                    .startDate(toCreateHackathon.getStartDate())
                    .endDate(toCreateHackathon.getEndDate())
                    .fee(toCreateHackathon.getFee())
                    .maxSize(toCreateHackathon.getMaxSize())
                    .minSize(toCreateHackathon.getMinSize())
                    .judges(judges)
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
                    .judges(hackathon.getJudges())
                    .build();
        }

    public List<HackathonResponseDto> map(final List<Hackathon> hackathons){
            List<HackathonResponseDto> hackathonResponseDtoList = new ArrayList<>();
                for(Hackathon hackathon : hackathons){
                    hackathonResponseDtoList.add(
                            map(hackathon)
                    );
                }
            return hackathonResponseDtoList;
    }



}

