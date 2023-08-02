package com.lk.election.dbTier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "election_party")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionParty {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "theme_colour")
    private String themeColour;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "symbol_img")
    private String symbolImg;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

}
