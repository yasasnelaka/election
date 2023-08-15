package com.lk.election.controllers.web;

import com.lk.election.dbTier.entities.*;
import com.lk.election.dbTier.repositories.*;
import com.lk.election.init.*;
import com.lk.election.models.requestModels.CandidateDeleteRequest;
import com.lk.election.models.requestModels.CandidateRegistrationRequest;
import com.lk.election.models.responseModels.AlertMessage;
import com.lk.election.models.responseModels.PageDetails;
import com.lk.election.models.responseModels.UserDetails;
import com.lk.election.services.SessionConfigService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            model.addAttribute("page_details", new PageDetails("SES | Candidates", "Candidates"));
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

        List<Candidate> candidateList = candidateRepository.findAll(Status.LIVE);
        List<com.lk.election.models.responseModels.Candidate> candidates = new ArrayList<>();
        for (Candidate candidate : candidateList) {
            ElectionParty party = this.electionPartyRepository.getById(candidate.getElectionPartyId());
            PollingDivision division = this.pollingDivisionRepository.getById(candidate.getPollingDivisionId());
            District district = this.districtRepository.getById(division.getDistrictId());
            Province province = this.provinceRepository.getById(district.getProvinceId());
            com.lk.election.models.responseModels.PollingDivision pollingDivision = new com.lk.election.models.responseModels.PollingDivision().convertEntityToResponseModel(division, district, province);
            candidates.add(new com.lk.election.models.responseModels.Candidate().convertEntityToResponseModel(candidate, new com.lk.election.models.responseModels.ElectionParty().convertEntityToResponseModel(party), pollingDivision));
        }
        model.addAttribute(ModelAttributes.CANDIDATES, candidates);
        return ViewHolder.CANDIDATES_LIST_VIEW;
    }

    @PostMapping(path = "/candidate-registration-request", consumes = MediaType.ALL_VALUE)
    public RedirectView candidateRegistration(HttpServletRequest servletRequest, CandidateRegistrationRequest request, RedirectAttributes redirectAttributes) throws ParseException {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        message.setType(FrontEndAlertType.SUCCESS);
        Candidate candidate = null;
        if (request.getFormType() == FormTypes.SAVE) {
            candidate = new Candidate();
            candidate.setCreatedAt(date);
            message.setMessage("Candidate has been saved successfully.");
        } else {
            candidate = candidateRepository.getById(request.getFormId());
            message.setMessage("Edited candidate details have been saved successfully.");
        }
        candidate.setElectionPartyId(request.getElectionParty());
        candidate.setPollingDivisionId(request.getPollingDivision());
        candidate.setNic(request.getNic());
        candidate.setElectionNumber(request.getElectionNumber());
        candidate.setFullName(request.getFullName());
        candidate.setGender((byte) request.getGender());
        candidate.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthday()));
        candidate.setEmail(request.getEmail());
        candidate.setMobile(request.getMobile());
        candidate.setAddress(request.getAddress());
        candidate.setUpdatedAt(date);
        candidate.setStatus(Status.LIVE);
        this.candidateRepository.saveAndFlush(candidate);
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.CANDIDATES_LIST_VIEW);
    }


    @PostMapping(path = "/candidate-delete", consumes = MediaType.ALL_VALUE)
    public RedirectView deleteBankUser(HttpServletRequest servletRequest, CandidateDeleteRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Candidate candidate = candidateRepository.getById(request.getId());
        if (candidate != null) {
            candidate.setUpdatedAt(date);
            candidate.setStatus(Status.DELETE);
            candidateRepository.saveAndFlush(candidate);
            message.setType(FrontEndAlertType.SUCCESS);
            message.setMessage("Candidate deletion has successful!");
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.CANDIDATES_LIST_VIEW);
    }

}
