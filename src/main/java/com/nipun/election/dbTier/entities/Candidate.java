package com.nipun.election.dbTier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "election_party_id")
    private Integer electionPartyId;

    @Column(name = "polling_division_id")
    private Integer pollingDivisionId;

    @Column(name = "nic")
    private String nic;

    @Column(name = "election_number")
    private Integer electionNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private java.lang.Byte gender;

    @Column(name = "birthday")
    private java.sql.Date birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

}
