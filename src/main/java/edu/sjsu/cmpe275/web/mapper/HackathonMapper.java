package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.stereotype.Component;

@Component
public class HackathonMapper {

        public Hackathon map(CreateHackathonRequestDto toCreateHackathon){
            return Hackathon.builder()
                    .name(toCreateHackathon.getName())
                    .description(toCreateHackathon.getDescription())
                    .start_date(toCreateHackathon.getStartDate())
                    .end_date(toCreateHackathon.getEndDate())
                    .fee(toCreateHackathon.getFee())
                    .max_size(toCreateHackathon.getMaxSize())
                    .min_size(toCreateHackathon.getMinSize())
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
                    .build();
        }

}
