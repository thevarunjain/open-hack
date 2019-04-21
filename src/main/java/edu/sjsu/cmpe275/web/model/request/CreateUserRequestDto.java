package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequestDto {

    @Valid
    private NameRequestDto name;

    @NotNull(message = "Email can not be null")
    @Email
    private String email;

    @NotNull(message = "Screen name can not be null")
    @Pattern(regexp = "[a-zA-Z0-9]{3,}", message = "Screen name should be of minimum 3 alphanumeric characters")
    private String screenName;

    @URL
    private String portraitURL;

    private String businessTitle;

    private String aboutMe;

    @Valid
    private AddressRequestDto address;
}
