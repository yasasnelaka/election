package com.lk.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {
    private int id;
    private String name;
    private String email;
    private int typeId;
    private String type;
}
