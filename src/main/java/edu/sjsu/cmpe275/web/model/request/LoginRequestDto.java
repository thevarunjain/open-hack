package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
