package com.nipun.election.models.responseModels;

import com.nipun.election.init.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectionCandidate extends Candidate implements Serializable {
    private int electionCandidateId;
    private int electionId;
    private int seatTypeId;
    private String seatType;
    private boolean isLeader;
    private int votes;

    public ElectionCandidate convertEntityToResponseModel(com.nipun.election.dbTier.entities.ElectionCandidate ec, Candidate candidate) {
        ElectionCandidate c = new ElectionCandidate(ec.getId(),ec.getElectionId(), ec.getSeatType(), SeatType.ALL[ec.getSeatType()], ec.getIsLeader() == 1, ec.getVotes());
        if (candidate != null) {
            c.setId(candidate.getId());
            c.setElectionParty(candidate.getElectionParty());
            c.setPollingDivision(candidate.getPollingDivision());
            c.setNic(candidate.getNic());
            c.setElectionNumber(candidate.getElectionNumber());
            c.setFullName(candidate.getFullName());
            c.setGenderId(candidate.getGenderId());
            c.setGender(candidate.getGender());
            c.setBirthday(candidate.getBirthday());
            c.setEmail(candidate.getEmail());
            c.setMobile(candidate.getMobile());
            c.setAddress(candidate.getAddress());
        }
        return c;
    }
}
