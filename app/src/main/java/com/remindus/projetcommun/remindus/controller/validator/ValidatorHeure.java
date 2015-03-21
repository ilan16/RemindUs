package com.remindus.projetcommun.remindus.controller.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ilanmalka on 17/03/15.
 */
public class ValidatorHeure {

    private static final String TIME24HOURS_PATTERN =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private Pattern pattern;
    private Matcher matcher;

    public ValidatorHeure() {
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    /**
     * Validate time in 24 hours format with regular expression
     *
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     */
    public boolean validate(final String time) {

        matcher = pattern.matcher(time);
        return matcher.matches();

    }
}


