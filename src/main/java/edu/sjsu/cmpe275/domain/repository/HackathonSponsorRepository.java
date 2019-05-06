package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface HackathonSponsorRepository
        extends JpaRepository<HackathonSponsor, HackathonSponsor.HackathonSponsorId> {

    List<HackathonSponsor> findByHackathonId (Hackathon id);
}

