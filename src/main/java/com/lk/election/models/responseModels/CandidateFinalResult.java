package com.lk.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateFinalResult implements Serializable {
    private int id;
    private String province;
    private String district;
    private String pollingDivision;
    private String party;
    private int number;
    private String fullName;
    private String gender;
    private int votes;
    private int isElected;
}
