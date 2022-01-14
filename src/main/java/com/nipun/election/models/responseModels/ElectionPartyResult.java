package com.nipun.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ElectionPartyResult implements Serializable {
    private int id;
    private String name;
    private String abbreviation;
    private String color;
    private int votes;
    private int seats;
    private double percentage;

    public String getDisplayName(){
        return this.name+" ("+this.abbreviation+")";
    }
    public String getDisplayPercentage(){
        return new DecimalFormat("##.##").format(this.percentage)+"%";
    }

    public String getProgressBarStyles(){
        return "width: "+this.getPercentage()+"%; background-color: "+this.color+";";
    }
}
