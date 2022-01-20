package com.nipun.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Election implements Serializable {
    private int id;
    private String name;
    private int year;
    private int maxSeats;
    private Date startTime;
    private User startedBy;
    private Date startedTime;
    private Date endTime;
    private User endedBy;
    private Date endedTime;
    private int votes;
    private int votesValid;
    private int votesInvalid;
    private int electionStatus;

    public Election convertEntityToResponseModel(com.nipun.election.dbTier.entities.Election e, User startedBy, User endedBy) {
        return new Election(e.getId(), e.getName(), e.getYear(), e.getMaxSeats(), e.getStartTime(), startedBy, e.getStartedTime(), e.getEndTime(), endedBy, e.getEndedTime(), e.getVotes(), e.getVotesValid(), e.getVotesInvalid(),e.getElectionStatus());
    }

    public String getStartedTimeToDisplay() {
        return this.startedTime == null ? "-" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startedTime);
    }

    public String getStartTimeToDisplay() {
        return this.startTime == null ? "-" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);
    }

    public String getEndTimeToDisplay() {
        return this.endTime == null ? "-" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
    }

    public String getEndedTimeToDisplay() {
        return this.endedTime == null ? "-" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endedTime);
    }

}
