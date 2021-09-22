package com.example.myproject;

public class ProfileUser {

    private String Username;
    private String Email;
    private String Password;
    private String PhoneNumber;

    public ProfileUser(String username, String email, String password, String phoneNumber) {
        Username = username;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
