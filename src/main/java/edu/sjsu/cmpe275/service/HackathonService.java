package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Hackathon;
import edu.sjsu.cmpe275.domain.entity.HackathonSponsor;
import edu.sjsu.cmpe275.domain.entity.Team;
import edu.sjsu.cmpe275.domain.entity.TeamMembership;
import edu.sjsu.cmpe275.domain.exception.HackathonNotFoundException;
import edu.sjsu.cmpe275.domain.repository.HackathonRepository;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateHackathonRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final HackathonSponsorMapper hackathonSponsorMapper;
    private final OrganizationService organizationService;
    private final HackathonSponsorService hackathonSponsorService;
    private final TeamRepository teamRepository;
    private final TeamMembershipService teamMembershipService;

    @Autowired
    public HackathonService(
            HackathonRepository hackathonRepository,
            HackathonSponsorMapper hackathonSponsorMapper,
            OrganizationService organizationService,
            HackathonSponsorService hackathonSponsorService,
            TeamRepository teamRepository,
            TeamMembershipService teamMembershipService
    ) {
        this.hackathonRepository = hackathonRepository;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.organizationService = organizationService;
        this.hackathonSponsorService = hackathonSponsorService;
        this.teamRepository = teamRepository;
        this.teamMembershipService = teamMembershipService;
    }

    public List<Hackathon> findHackathons(final String name) {
        if (Objects.nonNull(name))
            return hackathonRepository.findByNameContainingIgnoreCase(name);
        else
            return hackathonRepository.findAll();
    }

    public Hackathon findHackathon(final Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new HackathonNotFoundException(id));
    }

    @Transactional
    public Hackathon createHackathon(final Hackathon hackathon, final List<Long> sponsors, final List<Integer> discount) {

        Hackathon createdHackathon = hackathonRepository.save(hackathon);

        if (Objects.nonNull(sponsors) && Objects.nonNull(discount)) {
            for (int i = 0; i < sponsors.size(); i++) {
                HackathonSponsor createdSponsor = hackathonSponsorMapper.map(
                        findHackathon(createdHackathon.getId()),
                        organizationService.findOrganization(sponsors.get(i)),
                        discount.get(i));

                hackathonSponsorService.createSponsors(createdSponsor);
            }
        }


        return createdHackathon;
    }

    @Transactional
    public Hackathon updateHackathon(final Long id, @Valid UpdateHackathonRequestDto updateHackathon) {
        Hackathon hackathon = findHackathon(id);


        Date start = Objects.nonNull(updateHackathon.getStartDate())
                ? updateHackathon.getStartDate()
                : hackathon.getStartDate();

        Date end = Objects.nonNull(updateHackathon.getEndDate())
                ? updateHackathon.getEndDate()
                : hackathon.getEndDate();

        Date current = Objects.nonNull(updateHackathon.getCurrentDate())
                ? updateHackathon.getCurrentDate()
                : new Date();


        if (current.compareTo(start) == 1 && current.compareTo(end) == -1) {  // between start ned
            hackathon.setStatus("Closed");
        } else if (current.compareTo(start) == 0 || current.compareTo(end) == 0) {     // on start and end
            hackathon.setStatus("Closed");
        } else if (current.compareTo(start) == -1) {                 // before start
            hackathon.setStatus("Open");
        } else if (current.compareTo(end) == 1) {                    // after end
            hackathon.setStatus("Closed");
        }


        hackathon.setStartDate(Objects.nonNull(updateHackathon.getStartDate())
                ? updateHackathon.getStartDate()
                : hackathon.getStartDate()
        );

        hackathon.setEndDate(Objects.nonNull(updateHackathon.getEndDate())
                ? updateHackathon.getEndDate()
                : hackathon.getEndDate()
        );

        hackathon.setStatus(Objects.nonNull(updateHackathon.getToState())
                ? updateHackathon.getToState()
                : hackathon.getStatus());

        return hackathon;
    }

    public HackathonEarningReport getEarningReport(final Long id) {
        List<Float> fees = getRegistrationFee(id);
        Float paidRegFee = fees.get(0);
        Float unpaidRegFee = fees.get(1);
        Float sponsorRevenue = getSponsorRevenue(id);
        Float expense = getExpense(id);
        Float profit = paidRegFee + sponsorRevenue - expense;
        return HackathonEarningReport.builder()
                .paidRegistrationFee(paidRegFee)
                .unpaidRegistrationFee(unpaidRegFee)
                .sponsorRevenue(sponsorRevenue)
                .expense(expense)
                .profit(profit)
                .build();
    }

    private List<Float> getRegistrationFee(final Long id) {
        Hackathon hackathon = findHackathon(id);
        Float hackathonFee = hackathon.getFee();
        List<Team> teams = teamRepository.findByHackathon(hackathon);
        List<Float> fees = new ArrayList<>();
        float paidRegistrationFee = 0.0F;
        float unpaidRegistrationFee = 0.0F;
        // TODO This can be done using SQL queries
        for (Team team: teams) {
            List<TeamMembership> teamMembershipList = teamMembershipService.findTeamMembers(team);
            for (TeamMembership teamMembership: teamMembershipList) {
                if (teamMembership.getFeePaid()) {
                    paidRegistrationFee += teamMembership.getAmount();
                } else {
                    unpaidRegistrationFee += hackathonFee;
                }
            }
        }
        fees.add(paidRegistrationFee);
        fees.add(unpaidRegistrationFee);
        return fees;
    }

    private Float getSponsorRevenue(final Long id) {
        Hackathon hackathon = findHackathon(id);
        List<HackathonSponsor> hackathonSponsors = hackathonSponsorService.findHackathonSponsors(hackathon);
        return hackathonSponsors.size() * 1000.0F;
    }

    private Float getExpense(final Long id) {
        // TODO Implement after bonus feature implemented
        return 0.0F;
    }
}

