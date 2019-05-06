package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.web.model.response.HackathonSponsorResponseDto;
import org.springframework.stereotype.Component;

@Component
public class HackathonSponsorMapper {

        public HackathonSponsor map(final Hackathon hackathon, final Organization sponsors, final int discount){
            return HackathonSponsor.builder()
                    .id(mapSponsorId(hackathon, sponsors))
                    .hackathonId(hackathon)
                    .organizationId(sponsors)
                    .discount(discount)
                    .build();
        }


    private HackathonSponsor.HackathonSponsorId mapSponsorId(
            final Hackathon hackathon,
            final Organization sponsors
    ) {
        return HackathonSponsor.HackathonSponsorId.builder()
                .hackathonId(hackathon.getId())
                .sponsorId(sponsors.getId())
                .build();
    }

    public HackathonSponsorResponseDto map(String sponsor, int discount) {
            return HackathonSponsorResponseDto.builder()
                    .sponsor(sponsor)
                    .discount(discount)
                    .build();
        }
}
