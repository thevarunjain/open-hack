package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TeamNotFoundException extends RuntimeException{
    private final String ERROR_CODE = "TEAM_NOT_FOUND";

    private Long id;

    public TeamNotFoundException(final Long id) {
        super("Team not found.");
        this.id = id;
    }
}
