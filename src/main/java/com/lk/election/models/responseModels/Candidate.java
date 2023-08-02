package com.lk.election.models.responseModels;

import com.lk.election.init.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate implements Serializable {
    protected int id;
    protected ElectionParty electionParty;
    protected PollingDivision pollingDivision;
    protected String nic;
    protected int electionNumber;
    protected String fullName;
    protected int genderId;
    protected String gender;
    protected String birthday;
    protected String email;
    protected String mobile;
    protected String address;

    public Candidate convertEntityToResponseModel(com.lk.election.dbTier.entities.Candidate c, ElectionParty party, PollingDivision division) {
        return new Candidate(c.getId(), party, division, c.getNic(), c.getElectionNumber(), c.getFullName(), c.getGender(), Gender.ALL[c.getGender()], new SimpleDateFormat("yyyy-MM-dd").format(c.getBirthday()), c.getEmail(), c.getMobile(), c.getAddress());
    }
}
