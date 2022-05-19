package com.nasr.springsecurityclient;

public class Util {

    private static final String TOKEN_PREFiX="Bearer ";

    public static String getToken(String auth){
        return auth.replace(TOKEN_PREFiX,"");
    }
}
