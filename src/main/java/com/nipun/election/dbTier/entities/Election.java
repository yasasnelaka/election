package com.nipun.election.dbTier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "election")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Election {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "max_seats")
    private Integer maxSeats;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "started_by")
    private Integer startedBy;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "end_by")
    private Integer endBy;

    @Column(name = "ended_time")
    private Date endedTime;

    @Column(name = "votes")
    private Integer votes;

    @Column(name = "election_status")
    private Integer electionStatus;

    @Column(name = "votes_valid")
    private Integer votesValid;

    @Column(name = "votes_invalid")
    private Integer votesInvalid;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

}
