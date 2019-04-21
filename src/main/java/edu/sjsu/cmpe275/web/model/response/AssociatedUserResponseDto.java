package edu.sjsu.cmpe275.web.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssociatedUserResponseDto {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("screenName")
    private String screenName;
}
