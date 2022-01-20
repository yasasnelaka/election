package com.nipun.election.dbTier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "polling_division")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PollingDivision {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "polling_division")
    private String pollingDivision;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;
}
