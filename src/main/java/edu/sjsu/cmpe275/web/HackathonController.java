package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.security.CurrentUser;
import edu.sjsu.cmpe275.security.UserPrincipal;
import edu.sjsu.cmpe275.service.HackathonEarningReport;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.service.HackathonSponsorService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.web.mapper.HackathonMapper;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.request.UpdateHackathonRequestDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedSponsorResponseDto;
import edu.sjsu.cmpe275.web.model.response.HackathonMemberResponseDto;
import edu.sjsu.cmpe275.web.model.response.HackathonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/hackathons")
public class HackathonController {

    private final HackathonMapper hackathonMapper;
    private final HackathonService hackathonService;
    private final HackathonSponsorService hackathonSponsorService;
    private final UserService userService;
    private final HackathonSponsorMapper hackathonSponsorMapper;

    @Autowired
    public HackathonController(
            HackathonMapper hackathonMapper,
            HackathonService hackathonService,
            UserService userService,
            HackathonSponsorMapper hackathonSponsorMapper,
            HackathonSponsorService hackathonSponsorService
    ) {
        this.hackathonMapper = hackathonMapper;
        this.hackathonService = hackathonService;
        this.userService = userService;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.hackathonSponsorService = hackathonSponsorService;
    }

    @GetMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<HackathonResponseDto> getHackathons(
            @RequestParam(required = false) String name
    ) {

        List<Hackathon> allHackathons = hackathonService.findHackathons(name);

        return hackathonMapper.map(allHackathons);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto getHackathon(
            @PathVariable @NonNull Long id
    ) {
        Hackathon hackathon = hackathonService.findHackathon(id);
        HackathonEarningReport hackathonEarningReport = hackathonService.getEarningReport(id);
        List<HackathonSponsor> allHackathonSponsor = hackathonSponsorService.findHackathonSponsors(hackathon);
        List<AssociatedSponsorResponseDto> sponsorResponse = new ArrayList<>();
        for (HackathonSponsor hackathonSponsor : allHackathonSponsor) {
            sponsorResponse.add(
                    hackathonSponsorMapper.map(
                            hackathonSponsor.getId().getSponsorId(),
                            hackathonSponsor.getOrganizationId().getName(),
                            hackathonSponsor.getDiscount()
                    ));
        }
        return hackathonMapper.map(hackathon, sponsorResponse, hackathonEarningReport);
    }

    @GetMapping(value = "/{id}/members")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonMemberResponseDto getHackathonMembers(
            @PathVariable @NonNull Long id
    ) {

        List<User> participants = hackathonService.findHackathonParticipants(id);
        return hackathonMapper.map(
                hackathonService.findHackathon(id).getJudges(),
                participants
        );
    }

    @PostMapping(value = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto createHackathon(
            @Valid @RequestBody CreateHackathonRequestDto toCreateHackathon,
            @CurrentUser UserPrincipal currentUser
    ) {
        // TODO only admin can create hackathon
        User user = userService.findUser(currentUser.getId());
        List<User> judges = new ArrayList<>();
        for (Long id : toCreateHackathon.getJudges()) {
            judges.add(userService.findUser(id));
        }
        User owner = userService.findUser(user.getId());
        Hackathon hackathon = hackathonMapper.map(toCreateHackathon, judges, owner);
        Hackathon createdHackathon = hackathonService.createHackathon(
                hackathon,
                toCreateHackathon.getSponsors(),
                toCreateHackathon.getDiscount()
        );
        return hackathonMapper.map(createdHackathon);
    }

    @PatchMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonResponseDto updateHackathon(
            @Valid @RequestBody UpdateHackathonRequestDto updateHackathon,
            @NonNull @PathVariable Long id
    ) {
        // TODO Only admin should be able to update hackathon
        Hackathon hackathon = hackathonService.updateHackathon(id, updateHackathon);
        return hackathonMapper.map(hackathon);
    }
}
