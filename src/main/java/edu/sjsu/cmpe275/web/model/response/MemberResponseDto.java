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
public class MemberResponseDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long memberId;

    @JsonProperty("name")
    private NameResponseDto name;
}
