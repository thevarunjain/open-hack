package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrganizationRequestDto {

    @NotNull(message = "Organization name should be provided")
    private String name;

    private String description;

    @Valid
    private AddressRequestDto address;
}
