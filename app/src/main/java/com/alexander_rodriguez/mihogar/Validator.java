package com.alexander_rodriguez.mihogar;

import java.util.Calendar;
import java.util.Date;

public class Validator {

    public static int isEmptyOrNull(String ...data){
        int c = 0;
        for (String s: data) {
            if (s == null || s.isEmpty())
                return c;
            c++;
        }
        return -1;
    }

    //A resolver
    public static boolean isDateCorrect(Date d){
        return  true;
    }
}
