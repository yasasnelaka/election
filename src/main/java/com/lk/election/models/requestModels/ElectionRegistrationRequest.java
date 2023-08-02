package com.lk.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionRegistrationRequest {
    private int formId;
    private int formType;
    private String name;
    private int year;
    private int maxSeats;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "ElectionRegistrationRequest{" +
                "formId=" + formId +
                ", formType=" + formType +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", maxSeats=" + maxSeats +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getStartTime() {
        return this.startTime == null ? null : this.startTime.replace("T", " ")+":00";
    }

    public String getEndTime() {
        return this.endTime == null ? null : this.endTime.replace("T", " ")+":00";
    }

}
