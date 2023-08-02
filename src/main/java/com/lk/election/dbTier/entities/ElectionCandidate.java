package com.lk.election.dbTier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "election_candidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionCandidate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "election_id")
    private Integer electionId;

    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "seat_type")
    private Integer seatType;

    @Column(name = "is_leader")
    private Integer isLeader;

    @Column(name = "votes")
    private Integer votes;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

}
