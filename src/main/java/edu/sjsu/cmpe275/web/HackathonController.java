package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.HackathonSponsorService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/hackathon")
public class HackathonController {


    private HackathonMapper hackathonMapper;

    private HackathonService hackathonService;

    private HackathonSponsorService hackathonSponsorService;

    private UserService userService;

    private HackathonSponsorMapper hackathonSponsorMapper;


    @Autowired
    public HackathonController(
            HackathonMapper hackathonMapper,
            HackathonService hackathonService,
            UserService userService,
            HackathonSponsorMapper hackathonSponsorMapper,
            HackathonSponsorService hackathonSponsorService
    ){
        this.hackathonMapper = hackathonMapper;
        this.hackathonService = hackathonService;
        this.userService = userService;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.hackathonSponsorService = hackathonSponsorService;
    }




    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<HackathonResponseDto> getHackathons(@Valid @RequestBody String name){

        List<Hackathon> allHackathons = hackathonService.findHackathons();
        return hackathonMapper.map(allHackathons);


    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto getHackathon(@PathVariable @NonNull Long id){

          Hackathon hackathon =  hackathonService.findHackathon(id);
            return hackathonMapper.map(hackathon);
    }


    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto createHackathon(@Valid @RequestBody CreateHackathonRequestDto toCreateHackathon, Errors validationErrors){
        if(validationErrors.hasErrors()){
            //TODO Validate the error
        }

        Set<User> judges = new HashSet();
        for(Long id : toCreateHackathon.getJudges()){
            if(true){
                //TODO userService.findUser(id) validation
            }
            judges.add(userService.findUser(id));
        }

        Hackathon createdHackathon = hackathonService.createHackathon(hackathonMapper.map(toCreateHackathon,judges));

        //Can be changed
        for(int i=0;i<toCreateHackathon.getSponsors().size();i++){
                HackathonSponsor createdSponsor = hackathonSponsorMapper.map(hackathonService.findHackathon(createdHackathon.getId()), toCreateHackathon.getSponsors().get(i), toCreateHackathon.getDiscount().get(i));
                hackathonSponsorService.createSponsors(createdSponsor);
        }

        return hackathonMapper.map(createdHackathon);
    }

}


//@GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)