package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.*;
import com.nipun.election.dbTier.repositories.*;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.CandidateRegistrationRequest;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.UserDetails;
import com.nipun.election.services.SessionConfigService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CandidateController {


    private final SessionConfigService sessionConfigService;
    private final CandidateRepository candidateRepository;
    private final PollingDivisionRepository pollingDivisionRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final ElectionPartyRepository electionPartyRepository;


    public CandidateController(SessionConfigService sessionConfigService, CandidateRepository candidateRepository, PollingDivisionRepository pollingDivisionRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository, ElectionPartyRepository electionPartyRepository) {
        this.sessionConfigService = sessionConfigService;
        this.candidateRepository = candidateRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.electionPartyRepository = electionPartyRepository;
    }

    @GetMapping("/candidates-list")
    public String index(HttpSession session, Model model) {
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            model.addAttribute("page_details", new PageDetails("EVS | Citizens", "Citizens"));
            model.addAttribute("user", userDetails);
        }

        //BEGIN::Extracting Provinces
        List<Province> ps = this.provinceRepository.findAll();
        List<Map<String, String>> provinces = new ArrayList<>();
        for (Province province : ps) {
            Map<String, String> mm = new HashMap<>();
            mm.put("id", province.getId() + "");
            mm.put("name", province.getProvince());
            provinces.add(mm);
        }
        model.addAttribute(ModelAttributes.PROVINCES, provinces);
        //END::Extracting Provinces

        //BEGIN::Extracting Election Parties
        List<ElectionParty> electionParties = this.electionPartyRepository.findAll();
        List<Map<String, String>> parties = new ArrayList<>();
        for (ElectionParty party : electionParties) {
            Map<String, String> mm = new HashMap<>();
            mm.put("id", party.getId() + "");
            mm.put("name", party.getAbbreviation() + " (" + party.getName() + ")");
            parties.add(mm);
        }
        model.addAttribute(ModelAttributes.ELECTION_PARTIES, parties);
        //END::Extracting Election Parties

        List<Candidate> candidateList = candidateRepository.findAll();
        List<com.nipun.election.models.responseModels.Candidate> candidates = new ArrayList<>();
        for (Candidate candidate : candidateList) {
            ElectionParty party = this.electionPartyRepository.getById(candidate.getElectionPartyId());
            PollingDivision division = this.pollingDivisionRepository.getById(candidate.getPollingDivisionId());
            District district = this.districtRepository.getById(division.getDistrictId());
            Province province = this.provinceRepository.getById(district.getProvinceId());
            com.nipun.election.models.responseModels.PollingDivision pollingDivision = new com.nipun.election.models.responseModels.PollingDivision().convertEntityToResponseModel(division, district, province);
            candidates.add(new com.nipun.election.models.responseModels.Candidate().convertEntityToResponseModel(candidate, new com.nipun.election.models.responseModels.ElectionParty().convertEntityToResponseModel(party), pollingDivision));
        }
        model.addAttribute(ModelAttributes.CANDIDATES, candidates);
        return ViewHolder.CANDIDATES_LIST_VIEW;
    }

    @PostMapping(path = "/candidate-registration-request", consumes = MediaType.ALL_VALUE)
    public RedirectView candidateRegistration(HttpServletRequest servletRequest, CandidateRegistrationRequest request, Model model) {
//TODO::Implement the business process for citizenRegistration
        return new RedirectView(URLHolder.CANDIDATES_LIST_VIEW);
    }
}
