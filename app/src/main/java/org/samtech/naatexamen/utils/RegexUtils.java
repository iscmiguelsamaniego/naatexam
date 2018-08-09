package org.samtech.naatexamen.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isNotemptyValidate(String paramValue){
        String EMPTY = "^(?=\\s*\\S).*$";
        Pattern pattern = Pattern.compile(EMPTY);
        Matcher matcher = pattern.matcher(paramValue);
        return matcher.find();
    }
}
