package com.bootcamp.wellstudy.model;

public class Student {
    /**{
     "ssoId": "admindfdfdsfd",
     "firstName": "1112e312e",
     "lastName": "wddsf",
     "email": "teststudent@dsd.c",
     "userProfile": 2,
     "yearOfStudy": 1,
     "faculty": 1,
     "group": 1,
     "typeOfEducation": 1,
     "facultativeSubjects": []
     }
     */
    private String id;
    private String ssoId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer typeOfEducation;
    private Integer faculty;
    private Integer yearOfStudy;
    private Integer group;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", ssoId='" + ssoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typeOfEducation=" + typeOfEducation +
                ", faculty=" + faculty +
                ", yearOfStudy=" + yearOfStudy +
                ", group=" + group +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTypeOfEducation() {
        return typeOfEducation;
    }

    public void setTypeOfEducation(Integer typeOfEducation) {
        this.typeOfEducation = typeOfEducation;
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
}
