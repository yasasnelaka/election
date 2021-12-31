package com.nipun.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private int id;
    private String name;
    private int typeId;
    private String type;
}