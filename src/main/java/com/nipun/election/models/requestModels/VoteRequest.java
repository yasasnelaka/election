package com.nipun.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteRequest {
    private int electionId;
    private List<Integer> electionCandidates;

    @Override
    public String toString() {
        return "VoteRequest{" +
                "electionId=" + electionId +
                ", electionCandidates=" + electionCandidates +
                '}';
    }
}
