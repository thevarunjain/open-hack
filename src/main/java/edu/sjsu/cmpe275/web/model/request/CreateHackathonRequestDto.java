package edu.sjsu.cmpe275.web.model.request;

import edu.sjsu.cmpe275.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateHackathonRequestDto {

    @NotNull(message = "Hackathon name can not be null")
    private String name;

    @NotNull(message = "Start Date can not be null")
    @Temporal(TemporalType.DATE)
    private java.util.Date startDate;

    @NotNull(message = "End Date can not be null")
    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private java.util.Date endDate;

    @NotNull(message = "Description can not be null")
    @Size(min = 10)
    private String description;

    @NotNull(message = "Fee can not be null")
    private Float fee;

    @NotNull(message = "Minimum team size can not be null")
    private int minSize;

    @NotNull(message = "Maximum team size can not be null")
    private int maxSize;

    @NotNull(message = "Atleast one judge is required")
    private Set<Long> judges;

    private int status;


    // sponsors
    // discount
    // judges
}

