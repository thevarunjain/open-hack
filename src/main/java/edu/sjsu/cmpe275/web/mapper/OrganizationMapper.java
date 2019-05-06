package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Address;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.AddressRequestDto;
import edu.sjsu.cmpe275.web.model.request.CreateOrganizationRequestDto;
import edu.sjsu.cmpe275.web.model.response.AddressResponseDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedUserResponseDto;
import edu.sjsu.cmpe275.web.model.response.OrganizationResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper {
    public Organization map(final CreateOrganizationRequestDto toCreate) {
        return Organization.builder()
                .name(toCreate.getName())
                .description(toCreate.getDescription())
                .address(mapAddressRequest(toCreate.getAddress()))
                .build();
    }
    private Address mapAddressRequest(final AddressRequestDto address) {
        if (Objects.isNull(address)) {
            return Address.builder().build();
        }
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .build();
    }

    public OrganizationResponseDto map(final Organization organization) {
        return OrganizationResponseDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .owner(mapOwnerResponse(organization.getOwner()))
                .description(organization.getDescription())
                .address(mapAddressResponse(organization.getAddress()))
                .members(mapMembersResponse(organization))
                .build();
    }

    public List<OrganizationResponseDto> map(final List<Organization> organizations) {
        List<OrganizationResponseDto> organizationResponseDtoList = Objects.nonNull(organizations)
                ? organizations
                .stream()
                .map(organization -> map(organization))
                .collect(Collectors.toList()) : new ArrayList<>();
        return organizationResponseDtoList;
    }

    private AssociatedUserResponseDto mapOwnerResponse(final User user) {
        return AssociatedUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .screenName(user.getScreenName())
                .build();
    }

    private AddressResponseDto mapAddressResponse(final Address address) {
        if (Objects.isNull(address)) {
            return AddressResponseDto.builder().build();
        }
        return AddressResponseDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .build();
    }

    private List<AssociatedUserResponseDto> mapMembersResponse(final Organization organization) {
        List<AssociatedUserResponseDto> members = Objects.nonNull(organization.getMembers())
                ? organization.getMembers()
                .stream()
                .map(member -> AssociatedUserResponseDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .screenName(member.getScreenName())
                        .build()
                )
                .collect(Collectors.toList()) : new ArrayList<>();
        return members;
    }

}
