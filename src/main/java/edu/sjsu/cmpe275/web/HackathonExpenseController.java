package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonExpense;
import edu.sjsu.cmpe275.service.HackathonExpenseService;
import edu.sjsu.cmpe275.service.HackathonService;
import edu.sjsu.cmpe275.web.mapper.HackathonExpenseMapper;
import edu.sjsu.cmpe275.web.model.request.CreateHackathonExpenseRequestDto;
import edu.sjsu.cmpe275.web.model.response.HackathonExpenseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/hackathons")
public class HackathonExpenseController {
    private final HackathonService hackathonService;
    private final HackathonExpenseService hackathonExpenseService;
    private final HackathonExpenseMapper hackathonExpenseMapper;

    @Autowired
    public HackathonExpenseController(
            HackathonService hackathonService,
            HackathonExpenseService hackathonExpenseService,
            HackathonExpenseMapper hackathonExpenseMapper
    ) {
        this.hackathonService = hackathonService;
        this.hackathonExpenseService = hackathonExpenseService;
        this.hackathonExpenseMapper = hackathonExpenseMapper;
    }

    @GetMapping(value = "/{id}/expenses")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<HackathonExpenseResponseDto> getExpensesForHackathon(
            @PathVariable @NotNull Long id
    ) {
        Hackathon hackathon = hackathonService.findHackathon(id);
        List<HackathonExpense> hackathonExpenseList = hackathonExpenseService.findHackthonExpenses(hackathon);
        return hackathonExpenseMapper.map(hackathonExpenseList);
    }

    @PostMapping(value = "/{id}/expenses")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HackathonExpenseResponseDto createTeam(
            @Valid @RequestBody CreateHackathonExpenseRequestDto toCreateExpense,
            @PathVariable @NotNull Long id
    ) {
        Hackathon hackathon = hackathonService.findHackathon(id);
        HackathonExpense createdExpense = hackathonExpenseService.createExpense(
                hackathonExpenseMapper.map(toCreateExpense, hackathon)
        );
        return hackathonExpenseMapper.map(createdExpense);
    }
}
