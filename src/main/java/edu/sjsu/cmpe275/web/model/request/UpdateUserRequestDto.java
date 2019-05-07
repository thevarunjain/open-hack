package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserRequestDto {

    private NameRequestDto name;

    @URL
    private String portraitURL;

    private String businessTitle;

    private String aboutMe;

    private AddressRequestDto address;
}
