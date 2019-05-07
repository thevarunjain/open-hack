package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.HackathonSponsorService;
import edu.sjsu.cmpe275.service.OrganizationService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedSponsorResponseDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
        this.organizationService = organizationService;
    }


    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<HackathonResponseDto> getHackathons(){

        List<Hackathon> allHackathons = hackathonService.findHackathons();

        return hackathonMapper.map(allHackathons);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto getHackathonById(@PathVariable @NonNull Long id){

        Hackathon hackathon =  hackathonService.findHackathon(id);

        List<HackathonSponsor> allHackathonSponsor=  hackathonSponsorService.findHackathonSponsors(hackathon);

        List<AssociatedSponsorResponseDto> sponsorResponse = new ArrayList<>();

        for(HackathonSponsor hackathonSponsor : allHackathonSponsor){

            sponsorResponse.add(
                    hackathonSponsorMapper.map(
                            hackathonSponsor.getId().getSponsorId(),
                            hackathonSponsor.getOrganizationId().getName(),
                            hackathonSponsor.getDiscount()
                    ));

        }
        return hackathonMapper.map(hackathon, sponsorResponse);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Hackathon> getHackathonByName(@PathVariable @NonNull String name){

        List<Hackathon> hackathon =  hackathonService.findHackathonByName(name);
//        List<HackathonSponsor> allHackathonSponsor=  hackathonSponsorService.findHackathonSponsors(hackathon);

//        for(HackathonSponsor hackathonSponsor : allHackathonSponsor){
//
//            hackathonSponsor.getOrganizationId().getName();
//            hackathonSponsor.getDiscount();
//
//        }
//        return hackathonMapper.map(hackathon);
        return hackathon;
    }


    @PostMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto createHackathon(@Valid @RequestBody CreateHackathonRequestDto toCreateHackathon,
                                                Errors validationErrors,
                                                @NotNull @RequestParam Long ownerId){

        if(validationErrors.hasErrors()){
            //TODO Validate the error
        }

        Set<User> judges = new HashSet();
        for(Long id : toCreateHackathon.getJudges()){
            judges.add(userService.findUser(id));
        }
        User owner = userService.findUser(ownerId);

        Hackathon createdHackathon = hackathonService.createHackathon(
                hackathonMapper.map(toCreateHackathon,judges, owner),
                toCreateHackathon.getSponsors(),
                toCreateHackathon.getDiscount()
        );

        return hackathonMapper.map(createdHackathon);
    }




    @RequestMapping(value = "/{id}",
            produces = "application/json",
            method=RequestMethod.PATCH)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto updateHackathon(@Valid @RequestBody UpdateHackathonRequestDto updateHackathon,
                                Errors validationErrors, @NonNull @PathVariable Long id){

        if(validationErrors.hasErrors()){
            //TODO Validate the error
        }

        Hackathon hackathon =  hackathonService.updateHackathon(id, updateHackathon);

        return hackathonMapper.map(hackathon);
    }

}



