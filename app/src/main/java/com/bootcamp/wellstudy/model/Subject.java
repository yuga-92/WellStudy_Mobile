package com.bootcamp.wellstudy.model;

import java.util.Set;

public class Subject {
    private String subjectName;
    private Boolean isActive;
    private Boolean isFacultative;
    private String description;
    private Integer semester;
    private Integer facultyId;
    private Set<Integer> studentIds;

    public Subject(String subjectName, String description, Boolean isActive) {
        this.subjectName = subjectName;
        this.description = description;
        this.isActive = isActive;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getFacultative() {
        return isFacultative;
    }

    public void setFacultative(Boolean facultative) {
        isFacultative = facultative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Set<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
