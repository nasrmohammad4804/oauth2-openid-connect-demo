package com.nasr.springsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String sub;
    private ArrayList<String> aud;
    private String nbf;
    private ArrayList<String> scope;
    private String iss;
    private String exp;
    private String iat;
    private Long sso_id;

}
