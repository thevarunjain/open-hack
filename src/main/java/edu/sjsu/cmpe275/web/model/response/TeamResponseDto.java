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
public class TeamResponseDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hackathon")
    private HackathonResponseDto hackathon;

    @JsonProperty("owner")
    private AssociatedUserResponseDto owner;

    @JsonProperty("isFinalized")
    private Boolean isFinalized;

    @JsonProperty("submission_url")
    private String submissionURL;

    @JsonProperty("grades")
    private Float grades;

    @JsonProperty("members")
    private List<AssociatedMemberResponseDto> members;
}

