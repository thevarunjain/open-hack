package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonSponsorRepository extends JpaRepository<HackathonSponsor, Long > {
}
