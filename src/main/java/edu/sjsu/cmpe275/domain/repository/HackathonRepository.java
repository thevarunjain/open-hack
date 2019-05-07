package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    List<Hackathon> findByNameContainingIgnoreCase (String name);

}
