package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HackathonWithTeamResponseDto {

    @JsonProperty("hackathon")
    private HackathonResponseDto hackathon;

    @JsonProperty("team")
    private TeamResponseDto team;
}
