package com.lk.election.models.responseModels;

import com.lk.election.init.Gender;
import com.lk.election.init.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private int userTypeId;
    private String userTypeShort;
    private String userTypeMiddle;
    private String userTypeFull;
    private String email;
    private String firstName;
    private String lastName;
    private int genderId;
    private String gender;
    private String mobile;
    private String address;

    public User convertEntityToResponseModel(com.lk.election.dbTier.entities.User u){
        return new User(u.getId(),u.getUserTypeId(), UserTypes.ALL_TYPES_SHORT[u.getUserTypeId()], UserTypes.ALL_TYPES_MIDDLE[u.getUserTypeId()],
                UserTypes.ALL_TYPES_FULL[u.getUserTypeId()],u.getEmail(),u.getFirstName(),u.getLastName(),u.getGender(), Gender.ALL[u.getGender()],u.getMobile(),u.getAddress());
    }
    public String getFullName(){
        return this.firstName+" "+this.lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userTypeId=" + userTypeId +
                ", userTypeShort='" + userTypeShort + '\'' +
                ", userTypeMiddle='" + userTypeMiddle + '\'' +
                ", userTypeFull='" + userTypeFull + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", genderId=" + genderId +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
