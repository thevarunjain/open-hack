package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cmpe275.domain.entity.User;
import lombok.*;

import java.util.Set;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HackathonSponsorResponseDto {

    @JsonProperty("sponsor")
    private String sponsor;

    @JsonProperty("discount")
    private int discount;

}