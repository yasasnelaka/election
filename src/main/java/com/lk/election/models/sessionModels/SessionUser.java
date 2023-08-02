package com.lk.election.models.sessionModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser implements Serializable {
    private Integer id;
    private Integer userTypeId;
    private String userType;
    private String email;
    private String firstName;
    private String lastName;
    private Integer gender;
    private String mobile;
    private String address;

    public String getName(){
        return this.firstName+" "+this.lastName;
    }
}