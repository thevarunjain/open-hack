package edu.sjsu.cmpe275.web.util;

import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ValidatorUtil {

    public static void validateParams(final Map<String, String> params, final List<String> parameters)
            throws ConstraintViolationException {
        for (final String parameter : parameters) {
            if (!params.containsKey(parameter) || StringUtils.isEmpty(params.get(parameter))) {
                throw new ConstraintViolationException("Query parameter missing in request", parameter);
            }
        }
    }

    public static void validateRestrictedParam(final Map<String, String> params,
                                               final List<String> restrictedParams)
            throws ConstraintViolationException {
        for (final String parameter : restrictedParams) {
            if (params.containsKey(parameter))
                throw new ConstraintViolationException("Not allowed as query parameter", parameter);
        }

    }
}
