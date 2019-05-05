package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/hackathon")
public class HackathonController {


    private HackathonMapper hackathonMapper;

    private HackathonService hackathonService;

    @Autowired
    public HackathonController(
            HackathonMapper hackathonMapper,
            HackathonService hackathonService
    ){
        this.hackathonMapper = hackathonMapper;
        this.hackathonService = hackathonService;
    }




    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String  getHackathon(@Valid @RequestBody String name){

        return  name;
    }


    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto createHackathon(@Valid @RequestBody CreateHackathonRequestDto toCreateHackathon, Errors validationErrors){
        if(validationErrors.hasErrors()){
            //TODO Validate the error
        }
        Hackathon createdHackathon = hackathonService.createHackathon(hackathonMapper.map(toCreateHackathon));
        return hackathonMapper.map(createdHackathon);
    }

}


//@GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)