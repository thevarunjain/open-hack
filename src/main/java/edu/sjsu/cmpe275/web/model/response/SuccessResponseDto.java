package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Getter
@Data
@XmlRootElement
@NoArgsConstructor
public class SuccessResponseDto {
    @JsonProperty("success_message")
    private String responseMessage;
}
