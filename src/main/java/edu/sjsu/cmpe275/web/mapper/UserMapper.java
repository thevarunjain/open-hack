package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Address;
import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.domain.entity.User;
import edu.sjsu.cmpe275.web.model.request.AddressRequestDto;
import edu.sjsu.cmpe275.web.model.request.CreateUserRequestDto;
import edu.sjsu.cmpe275.web.model.response.AddressResponseDto;
import edu.sjsu.cmpe275.web.model.response.AssociatedOrganizationResponseDto;
import edu.sjsu.cmpe275.web.model.response.NameResponseDto;
import edu.sjsu.cmpe275.web.model.response.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User map(final CreateUserRequestDto toCreate) {
        return User.builder()
                .firstName(
                        Objects.nonNull(toCreate.getName())
                                ? toCreate.getName().getFirst()
                                : null
                )
                .lastName(
                        Objects.nonNull(toCreate.getName())
                                ? toCreate.getName().getLast()
                                : null
                )
                .email(toCreate.getEmail())
                .screenName(toCreate.getScreenName())
                .portraitURL(toCreate.getPortraitURL())
                .businessTitle(toCreate.getBusinessTitle())
                .aboutMe(toCreate.getAboutMe())
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

    public UserResponseDto map(final User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(mapNameResponse(user))
                .email(user.getEmail())
                .screenName(user.getScreenName())
                .portraitURL(user.getPortraitURL())
                .businessTitle(user.getBusinessTitle())
// TODO                .organization(mapOrganizationResponse(user.getOrganization()))
                .aboutMe(user.getAboutMe())
                .address(mapAddressResponse(user.getAddress()))
                .isAdmin(user.isAdmin())
                .build();
    }

    public List<UserResponseDto> map(final List<User> users) {
        List<UserResponseDto> userResponseDtoList = Objects.nonNull(users)
                ? users
                .stream()
                .map(user -> map(user))
                .collect(Collectors.toList()) : new ArrayList<>();
        return userResponseDtoList;
    }

    private NameResponseDto mapNameResponse(final User user) {
        if (Objects.isNull(user)) {
            return NameResponseDto.builder().build();
        }
        return NameResponseDto.builder()
                .first(user.getFirstName())
                .last(user.getLastName())
                .build();
    }

    private AssociatedOrganizationResponseDto mapOrganizationResponse(Organization organization) {
        if (Objects.isNull(organization)) {
            return AssociatedOrganizationResponseDto.builder().build();
        }
        return AssociatedOrganizationResponseDto.builder()
                .id(organization.getId())
                .name(organization.getName())
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

}
