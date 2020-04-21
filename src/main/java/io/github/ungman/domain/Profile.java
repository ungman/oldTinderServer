package io.github.ungman.domain;

import io.github.ungman.domain.enums.Gender;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PROFILE_USER")
public class Profile {

    @Id
    @Column(name = "ID_USER")
    private Long idUser;
    @Column(name = "USERNAME")
    private String name;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ID_SHOWN")
    private Long idShown;

    public Profile setEmptyShownId() {
        this.setIdShown(-1L);
        return this;
    }
}

