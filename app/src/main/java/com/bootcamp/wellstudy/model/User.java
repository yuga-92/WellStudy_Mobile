package com.bootcamp.wellstudy.model;

public class User {

    private String ssoId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer userProfile;


    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Integer userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "ssoId='" + ssoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userProfile=" + userProfile +
                '}';
    }
}
