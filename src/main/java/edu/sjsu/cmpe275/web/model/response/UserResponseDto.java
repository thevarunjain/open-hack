package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private NameResponseDto name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("screenName")
    private String screenName;

    @JsonProperty("portraitURL")
    private String portraitURL;

    @JsonProperty("businessTitle")
    private String businessTitle;

    @JsonProperty("aboutMe")
    private String aboutMe;

    @JsonProperty("ownerOf")
    private AssociatedOrganizationResponseDto ownerOf;

    @JsonProperty("memberOf")
    private AssociatedOrganizationResponseDto memberOf;

    @JsonProperty(value = "address")
    private AddressResponseDto address;

    // TODO Needed to add Ignore to avoid duplicate values
    @JsonProperty(value = "isAdmin")
    @JsonIgnore
    private boolean isAdmin;

}
