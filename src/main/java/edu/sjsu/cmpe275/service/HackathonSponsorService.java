package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.repository.HackathonSponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HackathonSponsorService {

    @Autowired
    private HackathonSponsorRepository hackathonSponsorRepository;

    public HackathonSponsorService(
            HackathonSponsorRepository hackathonSponsorRepository
    ){
        this.hackathonSponsorRepository = hackathonSponsorRepository;
    }


    public List<HackathonSponsor> findHackathonSponsors(final Hackathon id){
        return hackathonSponsorRepository.findByHackathonId(id);
    }

    public void createSponsors(HackathonSponsor hackathonSponsor){
        hackathonSponsorRepository.save(hackathonSponsor);
    }

    public HackathonSponsor findHackathonSponsor(final Hackathon hackathon, final Organization sponsor) {
        return hackathonSponsorRepository.findByHackathonIdAndOrganizationId(hackathon, sponsor)
                .orElse(null);
    }
}
