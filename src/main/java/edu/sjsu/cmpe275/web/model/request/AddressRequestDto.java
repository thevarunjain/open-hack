package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequestDto {
    private String street;

    private String city;

    private String state;

    @Size(max = 6)
    private String zip;
}
