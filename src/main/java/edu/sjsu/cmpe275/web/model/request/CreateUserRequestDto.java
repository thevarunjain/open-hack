package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // TODO Email address validation is not working
    @NotNull(message = "Email can not be null")
    @Email
    private String email;

    @NotNull(message = "Password can not be null")
    private String password;

    @NotNull(message = "Screen name can not be null")
    @Pattern(regexp = "[a-zA-Z0-9]{3,}", message = "Screen name should be of minimum 3 alphanumeric characters")
    private String screenName;
}
