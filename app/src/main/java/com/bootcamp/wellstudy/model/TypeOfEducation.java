package com.bootcamp.wellstudy.model;

public class TypeOfEducation {
    private static final String OFF_SITE = "Off-site";
    private static final String ON_SITE_COMMERTIONAL = "commertional";
    private static final String ON_SITE_SPONSORED = "sponsored";

    public static String getType(Integer type){
        String typeOfEducation;
        switch (type){
            case 1: typeOfEducation = OFF_SITE;
                break;
            case 2:typeOfEducation = ON_SITE_COMMERTIONAL;
                break;
            case 3:typeOfEducation = ON_SITE_SPONSORED;
                break;
            default: typeOfEducation = null;
        }
        return typeOfEducation;
    }

}
