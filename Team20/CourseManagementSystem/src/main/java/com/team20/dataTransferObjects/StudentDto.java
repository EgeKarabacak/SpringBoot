package com.team20.dataTransferObjects;

import javax.validation.constraints.NotEmpty;


public class StudentDto{
    private String username;
    @NotEmpty(message = "Password field cannot be empty")
    private String password;
    @NotEmpty(message = "Firstname Field is empty!")
    private String firstname;
    @NotEmpty(message = "Lastname Field is empty!")
    private String lastname;
    @NotEmpty(message = "Birthday field cannot be empty")
    private String birthday;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
