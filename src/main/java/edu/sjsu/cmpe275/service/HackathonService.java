package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.*;
import edu.sjsu.cmpe275.domain.exception.HackathonNotFoundException;
import edu.sjsu.cmpe275.domain.repository.HackathonRepository;
import edu.sjsu.cmpe275.domain.repository.TeamRepository;
import edu.sjsu.cmpe275.web.mapper.HackathonSponsorMapper;
import edu.sjsu.cmpe275.web.model.request.UpdateHackathonRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.*;

@Component
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final HackathonSponsorMapper hackathonSponsorMapper;
    private final OrganizationService organizationService;
    private final HackathonSponsorService hackathonSponsorService;
    private final TeamRepository teamRepository;
    private final TeamMembershipService teamMembershipService;
    private final HackathonExpenseService hackathonExpenseService;
    private final EmailService emailService;

    @Autowired
    public HackathonService(
            HackathonRepository hackathonRepository,
            HackathonSponsorMapper hackathonSponsorMapper,
            OrganizationService organizationService,
            HackathonSponsorService hackathonSponsorService,
            TeamRepository teamRepository,
            TeamMembershipService teamMembershipService,
            HackathonExpenseService hackathonExpenseService,
            EmailService emailService
    ) {
        this.hackathonRepository = hackathonRepository;
        this.hackathonSponsorMapper = hackathonSponsorMapper;
        this.organizationService = organizationService;
        this.hackathonSponsorService = hackathonSponsorService;
        this.teamRepository = teamRepository;
        this.teamMembershipService = teamMembershipService;
        this.hackathonExpenseService = hackathonExpenseService;
        this.emailService = emailService;
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


        if (current.compareTo(start) == 1 && current.compareTo(end) == -1) {  // between start end
            hackathon.setStatus("Closed");
        } else if (current.compareTo(start) == 0 || current.compareTo(end) == 0) {     // on start and end
            hackathon.setStatus("Closed");
        } else if (current.compareTo(start) == -1) {                 // before start
            hackathon.setStatus("Created");
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
        if (updateHackathon.getToState().equals("Finalized")) {
            List<Team> teams = teamRepository.findByHackathon(hackathon);
            teams.sort(Comparator.comparing(Team::getGrades).reversed());
            List<Team> winningTeams;
            List<Team> participatingTeams;
            if (teams.size() <= 3) {
                winningTeams = teams;
                participatingTeams = new ArrayList<>();
            } else {
                winningTeams = teams.subList(0, 3);
                participatingTeams = teams.subList(3, teams.size());
            }
            List<User> winningTeamMembers = findMembership(winningTeams);
            List<User> participatingTeamMembers = findMembership(participatingTeams);

            sendEmailToJudgesAndParticipants(hackathon.getJudges(), participatingTeamMembers, hackathon);
            sendEmailToWinners(winningTeamMembers, hackathon);
        }
        return hackathon;
    }

    private List<User> findMembership(List<Team> teams) {
        List<User> users = new ArrayList<>();
        for (Team team : teams) {
            List<TeamMembership> membershipList = teamMembershipService.findTeamMembers(team);
            for (TeamMembership membership : membershipList) {
                users.add(membership.getMemberId());
            }
        }
        return users;
    }

    private void sendEmailToJudgesAndParticipants(List<User> judges, List<User> members, Hackathon hackathon) {
        String subject = "Open-Hack 2019 Results available - " + hackathon.getName();
        String hostedServerUrl = "https://openhacks.herokuapp.com";

        for (User user : judges) {
            String message = " Hi,\n" +
                    "Welcome to Open Hackathon 2019\n" +
                    "Results for the Hackathon " + hackathon.getName() + "are now available at\n" +
                    "Log in to your account to take the action " + hostedServerUrl + "/login\n\n";
            emailService.sendSimpleMessage(user.getEmail(), subject, message);
        }

        for (User user : members) {
            String message = " Hi,\n" +
                    "Welcome to Open Hackathon 2019\n" +
                    "Results for the Hackathon " + hackathon.getName() + "are now available at\n" +
                    "Log in to your account to take the action " + hostedServerUrl + "/login\n\n";
            emailService.sendSimpleMessage(user.getEmail(), subject, message);
        }

    }

    private void sendEmailToWinners(List<User> winners, Hackathon hackathon) {
        String subject = "Open-Hack 2019 : Congratulations on winning " + hackathon.getName();
        String hostedServerUrl = "https://openhacks.herokuapp.com";

        for (User user : winners) {
            String message = " Hi,\n" +
                    "Welcome to Open Hackathon 2019\n" +
                    "You're one of the winning team! Congratulations!!" +
                    "Results for the Hackathon " + hackathon.getName() + "are now available at\n" +
                    "Log in to your account to take the action " + hostedServerUrl + "/login\n\n";
            emailService.sendSimpleMessage(user.getEmail(), subject, message);
        }
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
        List<HackathonExpense> expenses = hackathonExpenseService.findHackthonExpenses(findHackathon(id));
        Float totalExpense = 0.0F;
        for (HackathonExpense expense: expenses) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }

    public List<User> findHackathonParticipants(final Long id) {
        Hackathon hackathon = findHackathon(id);
        List<Team> teams = teamRepository.findByHackathon(hackathon);
        return findMembership(teams);
    }
}

