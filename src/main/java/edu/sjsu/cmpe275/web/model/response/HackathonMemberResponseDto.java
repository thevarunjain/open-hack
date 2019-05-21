package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HackathonMemberResponseDto {
    @JsonProperty(value = "judges")
    private List<MemberResponseDto> judges;

    @JsonProperty(value = "participants")
    private List<MemberResponseDto> participants;
}
