package com.bootcamp.wellstudy.model;

import java.util.Set;

/**
    "id": 1,
    "name": "Faculty1",
    "description": "FacultyDescription",
    "groups": [],
    "subjects": []
  }
 */
public class Faculty {
    private Integer id;
    private String name;
    private String description;
    private Set<Integer> groups;
    private Set<Integer> subjects;

    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Integer> getGroups() {
        return groups;
    }

    public void setGroups(Set<Integer> groups) {
        this.groups = groups;
    }

    public Set<Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Integer> subjects) {
        this.subjects = subjects;
    }
}
