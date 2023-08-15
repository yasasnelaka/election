package com.lk.election.dbTier.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "citizen")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Citizen {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "polling_division_id")
    private Integer pollingDivisionId;

    @Column(name = "nic")
    private String nic;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private int gender;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "fingerprint_id")
    private String fingerprintId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

}
