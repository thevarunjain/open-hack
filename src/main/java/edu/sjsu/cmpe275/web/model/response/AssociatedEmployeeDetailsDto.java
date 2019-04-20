package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement
public class AssociatedEmployeeDetailsDto {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("title")
    private String title;
}
