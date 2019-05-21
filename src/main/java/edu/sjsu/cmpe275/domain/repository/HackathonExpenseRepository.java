package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HackathonExpenseRepository extends JpaRepository<HackathonExpense, Long> {
    Optional<List<HackathonExpense>> findByHackathon(Hackathon hackathon);
}
