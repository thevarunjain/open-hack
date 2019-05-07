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
public class AssociatedSponsorResponseDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long sponsorId;

    @JsonProperty("name")
    private String sponsorName;

    @JsonProperty("discount")
    private int discount;
}
