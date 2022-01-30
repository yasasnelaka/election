package com.nipun.election.models.responseModels;

import com.nipun.election.dbTier.entities.District;
import com.nipun.election.dbTier.entities.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PollingDivision implements Serializable {
    private int pollingDivisionId;
    private String pollingDivisionName;
    private int districtDivisionId;
    private String districtDivisionName;
    private int provinceDivisionId;
    private String provinceDivisionName;
    private int seats;

    public PollingDivision convertEntityToResponseModel(com.nipun.election.dbTier.entities.PollingDivision division, District district, Province province){
        return new PollingDivision(division.getId(),division.getPollingDivision(),district.getId(),district.getDistrict(),province.getId(),province.getProvince(),division.getSeats());
    }
}
