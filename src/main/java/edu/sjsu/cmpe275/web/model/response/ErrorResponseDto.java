package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Data
@NoArgsConstructor
@XmlRootElement
public class ErrorResponseDto {

    @JsonProperty("error_code")
    private String responseCode;

    @JsonProperty("error_message")
    private String responseMessage;

    @JsonProperty("reference")
    private String reference;
}
