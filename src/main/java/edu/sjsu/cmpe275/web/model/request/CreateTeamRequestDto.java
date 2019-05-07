package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

//import javax.validation.Valid;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTeamRequestDto {

    @NotNull(message = "Team name can not be null")
    private String name;
    private List<Long> members;
    private List<String> roles;
    private Boolean isFinalized;
    private String submissionURL;
    private Float grades;

}
