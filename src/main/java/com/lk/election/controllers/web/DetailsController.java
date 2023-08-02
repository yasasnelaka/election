package com.lk.election.controllers.web;

import com.lk.election.dbTier.entities.District;
import com.lk.election.dbTier.entities.PollingDivision;
import com.lk.election.dbTier.repositories.DistrictRepository;
import com.lk.election.dbTier.repositories.PollingDivisionRepository;
import com.lk.election.models.requestModels.DistrictDivisionRequest;
import com.lk.election.init.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DetailsController {

    private final DistrictRepository districtRepository;
    private final PollingDivisionRepository pollingDivisionRepository;

    @Autowired
    public DetailsController(DistrictRepository districtRepository, PollingDivisionRepository pollingDivisionRepository) {
        this.districtRepository = districtRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
    }

    @RequestMapping(value = "/get-districts-by-province", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, String>>> getDistrictsByProvince(@RequestBody DistrictDivisionRequest request) {
        List<District> districts = this.districtRepository.findAllByProvince(request.getId(), Status.LIVE);
        List<Map<String, String>> l = new ArrayList<>();
        for (District district : districts) {
            Map<String, String> mm = new HashMap<>();
            mm.put("id", district.getId() + "");
            mm.put("name", district.getDistrict());
            l.add(mm);
        }
        return ResponseEntity.ok(l);
    }

    @RequestMapping(value = "/get-divisions-by-district", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, String>>> getPollingDivisionsByDistrict(@RequestBody DistrictDivisionRequest request) {
        List<PollingDivision> divisions = this.pollingDivisionRepository.findAllByDistrict(request.getId(), Status.LIVE);
        List<Map<String, String>> l = new ArrayList<>();
        for (PollingDivision division : divisions) {
            Map<String, String> mm = new HashMap<>();
            mm.put("id", division.getId() + "");
            mm.put("name", division.getPollingDivision());
            l.add(mm);
        }
        return ResponseEntity.ok(l);
    }

}
