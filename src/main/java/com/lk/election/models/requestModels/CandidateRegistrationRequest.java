package com.lk.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRegistrationRequest {
    private int formId;
    private int formType;
    private int pollingDivision;
    private int electionParty;
    private int electionNumber;
    private String nic;
    private String fullName;
    private int gender;
    private String birthday;
    private String email;
    private String mobile;
    private String address;

    @Override
    public String toString() {
        return "CandidateRegistrationRequest{" +
                "formId=" + formId +
                ", formType=" + formType +
                ", pollingDivision=" + pollingDivision +
                ", electionParty=" + electionParty +
                ", electionNumber=" + electionNumber +
                ", nic='" + nic + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
