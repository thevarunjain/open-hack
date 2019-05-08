package edu.sjsu.cmpe275.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HackathonResponseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private AssociatedUserResponseDto owner;

    @JsonProperty("description")
    private String description;

    @JsonProperty("startDate")
    private java.util.Date startDate;

    @JsonProperty("endDate")
    private java.util.Date endDate;

    @JsonProperty("fee")
    private Float fee;

    @JsonProperty("minSize")
    private int minSize;

    @JsonProperty("maxSize")
    private int maxSize;

//    @JsonProperty("judges")
//    private Set<User> judges;

    @JsonProperty(value = "judges")
//    @Singular
    private Set<AssociatedUserResponseDto> judges;
//    private Set<User> judges;

    @JsonProperty("status")
    private String status;

    @JsonProperty("sponsors")
    private List<AssociatedSponsorResponseDto> sponsors;


}