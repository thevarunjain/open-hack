package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.response.AssociatedOrganizationResponseDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedUserResponseDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationMembershipResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrganizationMembershipMapper {
    public OrganizationMembership map(final Organization organization, final User member) {
        return OrganizationMembership.builder()
                .id(mapOrganizationMembershipRequest(organization, member))
                .organization(organization)
                .member(member)
                .status("Pending")
                .build();
    }

    private OrganizationMembership.OrganizationMembershipId mapOrganizationMembershipRequest(
            final Organization organization,
            final User member
    ) {
        return OrganizationMembership.OrganizationMembershipId.builder()
                .organizationId(organization.getId())
                .memberId(member.getId())
                .build();
    }

    public OrganizationMembershipResponseDto map(
            final OrganizationMembership organizationMembership
    ) {
        return OrganizationMembershipResponseDto.builder()
                .id(organizationMembership.getId())
                .organization(mapOrganizationResponse(organizationMembership.getOrganization()))
                .member(mapMemberResponse(organizationMembership.getMember()))
                .status(organizationMembership.getStatus())
                .build();
    }

    private AssociatedOrganizationResponseDto mapOrganizationResponse(
            Organization organization
    ) {
        return AssociatedOrganizationResponseDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .build();
    }

    private AssociatedUserResponseDto mapMemberResponse(
            User member
    ) {
        return AssociatedUserResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .screenName(member.getScreenName())
                .build();
    }

    public List<OrganizationMembershipResponseDto> map(List<OrganizationMembership> organizationMembershipList) {
        List<OrganizationMembershipResponseDto> organizationMembershipResponseDtoList =
                Objects.nonNull(organizationMembershipList)
                        ? organizationMembershipList
                        .stream()
                        .map(organizationMembership -> map(organizationMembership))
                        .collect(Collectors.toList()) : new ArrayList<>();
        return organizationMembershipResponseDtoList;
    }
}
