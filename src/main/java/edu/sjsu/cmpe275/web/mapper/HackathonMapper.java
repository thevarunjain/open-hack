package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import org.springframework.stereotype.Component;

@Component
public class HackathonMapper {

        public Hackathon map(){
            return Hackathon.builder()

                    .build();
        }

}
