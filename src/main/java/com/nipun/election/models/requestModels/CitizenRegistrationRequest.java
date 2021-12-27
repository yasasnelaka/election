package com.nipun.election.models.requestModels;

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
}
