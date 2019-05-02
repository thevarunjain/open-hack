package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateHackathonRequestDto {

    @NotNull(message = "Hackathon name can not be null")
    private String name;

    @DateTimeFormat
    @NotNull(message = "Email can not be null")
    @Size(min = 10)
    private String description;

    @NotNull(message = "Fee can not be null")
    private Float fee;

    @NotNull(message = "Minimum team size can not be null")
    private String min_size;

    @NotNull(message = "Maximum team size can not be null")
    private String max_size;

    private int status;
}
