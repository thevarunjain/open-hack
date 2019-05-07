package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class HackathonNotFoundException extends RuntimeException {
    private final String ERROR_CODE = "HACKATHON_NOT_FOUND";

    private Long id;

    public HackathonNotFoundException(final Long id){
        super("Hackathon not found");
        this.id = id;
    }
}

