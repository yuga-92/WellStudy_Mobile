package com.bootcamp.wellstudy.model;

/**
 * Created by yurii.halych on 9/8/2016.
 *  {
 "id": 1,
 "year": 1,
 "name": "Group1",
 "faculty": 1,
 "students": [],
 "subjects": []
 }
 */
public class Group {
    private Integer id;
    private Integer year;
    private String name;
    private Integer faculty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
