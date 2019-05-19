package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.web.model.response.AssociatedSponsorResponseDto;
import org.springframework.stereotype.Component;

@Component
public class HackathonSponsorMapper {

    public HackathonSponsor map(final Hackathon hackathon, final Organization sponsors, final int discount) {
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

    public AssociatedSponsorResponseDto map(final Long sponsorId, final String name, final int discount) {
        return AssociatedSponsorResponseDto.builder()
                .sponsorId(sponsorId)
                .sponsorName(name)
                .discount(discount)
                .build();
    }

}
