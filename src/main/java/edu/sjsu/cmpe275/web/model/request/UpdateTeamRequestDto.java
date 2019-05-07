package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTeamRequestDto {

    private Float grades;

    private String submissionURL;

    private Boolean isFinalized;


}
