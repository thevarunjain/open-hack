package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Data
@ToString
@Builder
@NoArgsConstructor
public class ErrorResponseDto {

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("reference")
    private String reference;
}
