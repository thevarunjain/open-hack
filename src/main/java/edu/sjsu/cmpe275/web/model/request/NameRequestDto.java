package edu.sjsu.cmpe275.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameRequestDto {
    private String first;

    private String last;
}
