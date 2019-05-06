package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.exception.HackathonNotFoundException;
import edu.sjsu.cmpe275.domain.repository.HackathonRepository;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateHackathonRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Component
public class HackathonService {

    private HackathonRepository hackathonRepository;
    private HackathonSponsorMapper hackathonSponsorMapper;
    private OrganizationService organizationService;
    private HackathonSponsorService hackathonSponsorService;

    @Autowired
    public HackathonService(
            HackathonRepository hackathonRepository,
            HackathonSponsorMapper hackathonSponsorMapper,
            OrganizationService organizationService,
            HackathonSponsorService hackathonSponsorService

    ){
        this.hackathonRepository = hackathonRepository;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.organizationService = organizationService;
        this.hackathonSponsorService = hackathonSponsorService;
    }

    public List<Hackathon> findHackathons(){
        return hackathonRepository.findAll();
    }

    public Hackathon findHackathon(final long id){
        return hackathonRepository.findById(id)
                .orElseThrow(()-> new HackathonNotFoundException(id));
    }

    @Transactional
    public Hackathon createHackathon(final Hackathon hackathon, final List<Long> sponsors, final List<Integer> discount){

        Hackathon createdHackathon = hackathonRepository.save(hackathon);

        for(int i=0;i<sponsors.size();i++){
            HackathonSponsor createdSponsor = hackathonSponsorMapper.map(
                    findHackathon(createdHackathon.getId()),
                    organizationService.findOrganization(sponsors.get(i)),
                    discount.get(i));

           hackathonSponsorService.createSponsors(createdSponsor);
        }

        return createdHackathon;
    }

    @Transactional
    public Hackathon updateHackathon(final Long id, @Valid UpdateHackathonRequestDto updateHackathon){
        Hackathon hackathon = findHackathon(id);

        hackathon.setStartDate(Objects.nonNull(updateHackathon.getStartDate())
                                      ? updateHackathon.getStartDate()
                                      : hackathon.getStartDate()
                                );

        hackathon.setEndDate(Objects.nonNull(updateHackathon.getEndDate())
                                    ? updateHackathon.getEndDate()
                                    : hackathon.getEndDate()
                                );

        return hackathon;
    }

}

