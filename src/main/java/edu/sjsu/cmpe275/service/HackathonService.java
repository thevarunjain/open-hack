package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.exception.HackathonNotFoundException;
import edu.sjsu.cmpe275.domain.repository.HackathonRepository;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class HackathonService {

    private HackathonRepository hackathonRepository;

    @Autowired
    public HackathonService(
            HackathonRepository hackathonRepository
    ){
        this.hackathonRepository = hackathonRepository;
    }

    public List<Hackathon> findHackathons(){
        return hackathonRepository.findAll();
    }

    public Hackathon findHackathon(final long id){
        return hackathonRepository.findById(id)
                .orElseThrow(()-> new HackathonNotFoundException(id));
    }

    @Transactional
    public Hackathon createHackathon(final Hackathon hackathon){
        return hackathonRepository.save(hackathon);
    }

}
