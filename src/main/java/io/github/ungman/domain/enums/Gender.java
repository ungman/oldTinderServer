package io.github.ungman.domain.enums;

public enum Gender {

    MALE("сударь"),
    FEMALE("сударыня");
    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }


}
