package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.District;
import com.nipun.election.dbTier.entities.PollingDivision;
import com.nipun.election.dbTier.repositories.DistrictRepository;
import com.nipun.election.dbTier.repositories.PollingDivisionRepository;
import com.nipun.election.dbTier.repositories.ProvinceRepository;
import com.nipun.election.init.Status;
import com.nipun.election.models.requestModels.DistrictDivisionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
