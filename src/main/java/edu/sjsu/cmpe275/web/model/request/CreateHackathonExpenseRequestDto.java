package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateHackathonExpenseRequestDto {
    @NotNull(message = "Expense title should be provided")
    private String title;

    private String description;

    @NotNull(message = "Amount can not be null")
    private Float amount;

    @NotNull(message = "Date can not be null")
    @Temporal(TemporalType.DATE)
    private java.util.Date date;
}
