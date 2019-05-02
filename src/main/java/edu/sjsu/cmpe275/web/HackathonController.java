package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/hackathon")
public class HackathonController {


    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String  getHackathon(@Valid @RequestBody String name){
        return  name;
    }


//    @PostMapping(value = "")
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public void createHackathon(@Valid @RequestBody CreateHackathonRequestDTO  hackathon, Errors validationErrors){
//        if(validationErrors.hasErrors()){
//            //TODO Validate the error
//        }
//
//
//
//    }

}


//@GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)