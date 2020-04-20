package io.github.ungman.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="PROFILE_LIKE_MATCH")
public class ProfileMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_FIRST_USER")
    private Long idFirstUser;
    @Column(name = "ID_SECOND_USER")
    private Long idSecondUser;
}
