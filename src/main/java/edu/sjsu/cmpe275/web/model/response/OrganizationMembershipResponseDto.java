package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cmpe275.domain.entity.OrganizationMembership;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrganizationMembershipResponseDto {
    @JsonProperty("id")
    private OrganizationMembership.OrganizationMembershipId id;

    @JsonProperty("organization")
    private AssociatedOrganizationResponseDto organization;

    @JsonProperty("member")
    private AssociatedUserResponseDto member;

    @JsonProperty("status")
    private String status;
}
