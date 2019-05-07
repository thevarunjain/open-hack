package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssociatedMemberResponseDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long memberId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("role")
    private String role;

    @JsonProperty("amount")
    private Float amount;

    @JsonProperty("fee_paid")
    private Boolean fee_paid;

}