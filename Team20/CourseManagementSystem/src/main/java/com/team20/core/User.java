package com.team20.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class User {
    private String username, password;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int uid;
    @Column
    @ElementCollection
    protected List<String> roles;
    private boolean enabled;

    // For JPA
    protected User() {}

    public User( String un, String pw, boolean en) {
        username = un;
        password = pw;
        enabled = en;
    }

    public int getId() {return uid;}
    public String getUsername() { return username; }
    public void setUsername(String un) { username = un; }
    public String getPassword() { return password; }
    public boolean verifyLogin(String u, String pw) {return (u.equals(username) && pw.equals(password)); }
    public List<String> getRoles() { return roles; }

    public abstract void accept(Visitor v);

    public boolean getEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
    }

    public abstract String getType();
}
