package com.nipun.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
