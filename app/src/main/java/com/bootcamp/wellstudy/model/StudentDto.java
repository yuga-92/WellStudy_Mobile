package com.bootcamp.wellstudy.model;

public class StudentDto {
    /**{
     "ssoId": "student14",
     "firstName": "St14FN",
     "lastName": "St14LN",
     "email": "student14@xyz.com",
     "password": "abc125",
     "facultyId": 1,
     "groupId": 7,
     "typeOfEducationId": 3
     }
     */
    private String ssoId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer facultyId;
    private Integer groupId;
    private Integer typeOfEducationId;

    @Override
    public String toString() {
        return "StudentDto{" +
                "ssoId='" + ssoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", facultyId=" + facultyId +
                ", groupId=" + groupId +
                ", typeOfEducationId=" + typeOfEducationId +
                '}';
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

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTypeOfEducationId() {
        return typeOfEducationId;
    }

    public void setTypeOfEducationId(Integer typeOfEducationId) {
        this.typeOfEducationId = typeOfEducationId;
    }
}
