package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.HackathonSponsorService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import edu.sjsu.cmpe275.web.model.response.HackathonSponsorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;

@RestController
@RequestMapping(value = "/hackathons")
public class HackathonController {


    private HackathonMapper hackathonMapper;

    private HackathonService hackathonService;

    private HackathonSponsorService hackathonSponsorService;

    private UserService userService;

    private OrganizationService organizationService;

    private HackathonSponsorMapper hackathonSponsorMapper;

    private HackathonSponsorResponseDto hackathonSponsorResponseDto;


    @Autowired
    public HackathonController(
            HackathonMapper hackathonMapper,
            HackathonService hackathonService,
            UserService userService,
            HackathonSponsorMapper hackathonSponsorMapper,
            HackathonSponsorService hackathonSponsorService,
            HackathonSponsorResponseDto hackathonSponsorResponseDto
    ){
        this.hackathonMapper = hackathonMapper;
        this.hackathonService = hackathonService;
        this.userService = userService;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.hackathonSponsorService = hackathonSponsorService;
        this.organizationService = organizationService;
        this.hackathonSponsorResponseDto = hackathonSponsorResponseDto;
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
        List<HackathonSponsor> allHackathonSponsor=  hackathonSponsorService.findHackathonSponsors(hackathon);

        for(HackathonSponsor hackathonSponsor : allHackathonSponsor){
            hackathonSponsorMapper.map(
                    hackathonSponsor.getOrganizationId().getName(),
                    hackathonSponsor.getDiscount()
            );
        }

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
            judges.add(userService.findUser(id));
        }

        Hackathon createdHackathon = hackathonService.createHackathon(
                hackathonMapper.map(toCreateHackathon,judges),
                toCreateHackathon.getSponsors(),
                toCreateHackathon.getDiscount()
        );

        return hackathonMapper.map(createdHackathon);
    }

}



