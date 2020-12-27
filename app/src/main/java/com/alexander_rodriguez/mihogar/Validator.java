package com.alexander_rodriguez.mihogar;

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
}
