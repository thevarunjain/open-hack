package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonExpense;
import edu.sjsu.cmpe275.domain.repository.HackathonExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class HackathonExpenseService {
    @Autowired
    private HackathonExpenseRepository hackathonExpenseRepository;

    public HackathonExpenseService(
            HackathonExpenseRepository hackathonExpenseRepository
    ) {
        this.hackathonExpenseRepository = hackathonExpenseRepository;
    }

    public List<HackathonExpense> findHackthonExpenses(
            final Hackathon hackathon
    ) {
        return hackathonExpenseRepository.findByHackathon(hackathon)
                .orElse(null);
    }

    @Transactional
    public HackathonExpense createExpense(
            final HackathonExpense hackathonExpense
    ) {
        return hackathonExpenseRepository.save(hackathonExpense);
    }
}
