package com.ncbagroup.main.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String toSentenceCase(String input) {

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }


}
