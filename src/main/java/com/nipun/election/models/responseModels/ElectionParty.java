package com.nipun.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionParty implements Serializable {
    private int id;
    private String name;
    private String abbreviation;
    private String themeColor;

    public ElectionParty convertEntityToResponseModel(com.nipun.election.dbTier.entities.ElectionParty party){
        return new ElectionParty(party.getId(),party.getName(),party.getAbbreviation(),party.getThemeColour());
    }

    public String getNameToDisplay(){
        return this.name+" ("+this.abbreviation+")";
    }

}
