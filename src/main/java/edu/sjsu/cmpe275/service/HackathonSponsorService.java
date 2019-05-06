package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.repository.HackathonSponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HackathonSponsorService {

    @Autowired
    private HackathonSponsorRepository hackathonSponsorRepository;

    public HackathonSponsorService(
            HackathonSponsorRepository hackathonSponsorRepository
    ){
        this.hackathonSponsorRepository = hackathonSponsorRepository;
    }


    public void createSponsors(HackathonSponsor hackathonSponsor){
        hackathonSponsorRepository.save(hackathonSponsor);
    }
}
