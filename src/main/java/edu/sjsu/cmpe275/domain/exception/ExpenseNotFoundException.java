package edu.sjsu.cmpe275.domain.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ExpenseNotFoundException extends RuntimeException  {
    private final String ERROR_CODE = "EXPENSE_NOT_FOUND";

    private Long id;

    public ExpenseNotFoundException(final Long id) {
        super("Expense not found");
        this.id = id;
    }
}
