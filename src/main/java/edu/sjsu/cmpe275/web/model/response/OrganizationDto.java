package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrganizationDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private AssociatedUserResponseDto owner;

    @JsonProperty("description")
    private String description;

    @JsonProperty(value = "address")
    private AddressResponseDto address;

    @JsonProperty(value = "members")
    @Singular
    private List<AssociatedUserResponseDto> members;

}
