package com.team20.core;

import java.util.Arrays;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {

    private static Administrator instance;

    public Administrator() {}
    
    private Administrator(String un, String pw) {
        super(un, pw, true);
        roles = Arrays.asList("ROLE_ADMIN");
    }

    public static Administrator getInstance(String un, String pw) {
        if(instance == null) {
            instance = new Administrator(un, pw);
        }
        return instance;
    }

    public void accept(Visitor v) {v.visit(this);}

    public String getType()
    {
        return "Administrator";
    }
}