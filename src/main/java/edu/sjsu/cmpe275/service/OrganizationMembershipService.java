package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.domain.exception.OrganizationMembershipNotFoundException;
import edu.sjsu.cmpe275.domain.repository.OrganizationMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Component
public class OrganizationMembershipService {
    private OrganizationMembershipRepository organizationMembershipRepository;
    private EmailService emailService;

    @Autowired
    public OrganizationMembershipService(
            final OrganizationMembershipRepository organizationMembershipRepository,
            final EmailService emailService
    ) {
        this.organizationMembershipRepository = organizationMembershipRepository;
        this.emailService = emailService;
    }

    public OrganizationMembership findOrganizationMembership(
            final Organization organization,
            final User member
    ) {
        // This is used to find particular combination of organization and member ID
        OrganizationMembership.OrganizationMembershipId toFind =
                OrganizationMembership.OrganizationMembershipId.builder()
                        .organizationId(organization.getId())
                        .memberId(member.getId())
                        .build();
        return organizationMembershipRepository.findById(toFind)
                .orElseThrow(() -> new OrganizationMembershipNotFoundException(toFind));
    }

    public List<OrganizationMembership> findOrganizationMemberships(
            final Organization organization,
            final String status
    ) {
        // This is used to find membership of given organization based on status
        if (Objects.nonNull(status))
            return organizationMembershipRepository.findByOrganizationIdAndStatus(
                    organization.getId(),
                    status
            );
        else
            return organizationMembershipRepository.findByOrganizationId(organization.getId());
    }


    public OrganizationMembership findOrganizationByMemberAndStatus(
            final User user,
            final String status
    ) {
        return organizationMembershipRepository.findByMemberIdAndStatus(user.getId(), status)
                .orElse(null);
    }

    @Transactional
    public OrganizationMembership createOrganizationMembership(final OrganizationMembership newOrganizationMembership,
                                                               final String emailOwner) {

        String memberName = newOrganizationMembership.getMember().getFirstName() + " " + newOrganizationMembership.getMember().getLastName();
        String subject = "Open Hackathon 2019 - Join Request by " + memberName;
        String localServerUrl = "http://localhost:3000";
        String hostedServerUrl = "";

        String message = "Hello\n" +
                "You have one new join request by " + memberName + " " +
                "for you organization " + newOrganizationMembership.getOrganization().getName() + ".\n" +
                "Log in to your account to take the action " + localServerUrl + "/login\n\n" + "" +
                "Happy Hacking :)";

        emailService.sendSimpleMessage(emailOwner, subject, message);
        return organizationMembershipRepository.save(newOrganizationMembership);
    }

    @Transactional
    public OrganizationMembership updateOrganizationMembership(
            final Organization organization,
            final User member,
            final String toState
    ) {
        OrganizationMembership existingOrganizationMembership = findOrganizationMembership(organization, member);
        existingOrganizationMembership.setStatus(toState);
        return existingOrganizationMembership;
    }
}
