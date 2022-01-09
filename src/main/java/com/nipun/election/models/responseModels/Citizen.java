package com.nipun.election.models.responseModels;


import com.nipun.election.init.Gender;
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
public class Citizen implements Serializable {
    private int id;
    private PollingDivision pollingDivision;
    private String nic;
    private String fullName;
    private int genderId;
    private String gender;
    private String birthday;
    private String email;
    private String mobile;
    private String address;

    public Citizen convertEntityToResponseModel(com.nipun.election.dbTier.entities.Citizen c, PollingDivision pd) {
        return new Citizen(c.getId(), pd, c.getNic(), c.getFullName(), c.getGender(), Gender.ALL[c.getGender()], new SimpleDateFormat("yyyy-MM-dd").format(c.getBirthday()), c.getEmail(), c.getMobile(), c.getAddress());
    }
}
