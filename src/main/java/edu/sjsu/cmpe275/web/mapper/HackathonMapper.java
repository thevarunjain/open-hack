package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HackathonMapper {

        public Hackathon map(CreateHackathonRequestDto toCreateHackathon, Set<User> judges){
            return Hackathon.builder()
                    .name(toCreateHackathon.getName())
                    .description(toCreateHackathon.getDescription())
                    .start_date(toCreateHackathon.getStartDate())
                    .end_date(toCreateHackathon.getEndDate())
                    .fee(toCreateHackathon.getFee())
                    .max_size(toCreateHackathon.getMaxSize())
                    .min_size(toCreateHackathon.getMinSize())
                    .judges(judges)
                    .build();
        }


    public HackathonResponseDto map(Hackathon hackathon){
            return HackathonResponseDto.builder()
                    .id(hackathon.getId())
                    .name(hackathon.getName())
                    .description(hackathon.getDescription())
                    .startDate(hackathon.getStart_date())
                    .endDate(hackathon.getEnd_date())
                    .maxSize(hackathon.getMax_size())
                    .minSize(hackathon.getMin_size())
                    .fee(hackathon.getFee())
                    .judges(hackathon.getJudges())
                    .build();
        }

}
