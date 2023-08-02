package com.lk.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CitizenRegistrationRequest {
    private int formId;
    private int formType;
    private int pollingDivision;
    private String nic;
    private String fullName;
    private int gender;
    private String birthday;
    private String email;
    private String mobile;
    private String address;
    private String fingerprintId;

    @Override
    public String toString() {
        return "CitizenRegistrationRequest{" +
                "formId=" + formId +
                ", formType=" + formType +
                ", pollingDivision=" + pollingDivision +
                ", nic='" + nic + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", fingerprintId='" + fingerprintId + '\'' +
                '}';
    }
}
