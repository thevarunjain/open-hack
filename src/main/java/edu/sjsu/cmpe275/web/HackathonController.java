package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/hackathon")
public class HackathonController {


    @GetMapping(value = "abc", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void getHackathon(@RequestParam Long id){

        return;
    }


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public Hackathon createHackathon(@RequestParam Map<String, String> params){

    }

}


//@GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)