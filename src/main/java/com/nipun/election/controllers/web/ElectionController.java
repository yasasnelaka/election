package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.*;
import com.nipun.election.dbTier.repositories.*;
import com.nipun.election.init.*;
import com.nipun.election.models.requestModels.*;
import com.nipun.election.models.responseModels.AlertMessage;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.User;
import com.nipun.election.models.responseModels.UserDetails;
import com.nipun.election.services.SessionConfigService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ElectionController {

    private final SessionConfigService sessionConfigService;
    private final ElectionRepository electionRepository;
    private final CitizenRepository citizenRepository;
    private final CandidateRepository candidateRepository;
    private final PollingDivisionRepository pollingDivisionRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final ElectionPartyRepository electionPartyRepository;
    private final ElectionCandidateRepository electionCandidateRepository;

    public ElectionController(SessionConfigService sessionConfigService, ElectionRepository electionRepository, CandidateRepository candidateRepository, PollingDivisionRepository pollingDivisionRepository,
                              DistrictRepository districtRepository, ProvinceRepository provinceRepository, ElectionPartyRepository electionPartyRepository,
                              ElectionCandidateRepository electionCandidateRepository,
                              CitizenRepository citizenRepository) {
        this.sessionConfigService = sessionConfigService;
        this.electionRepository = electionRepository;
        this.citizenRepository = citizenRepository;
        this.candidateRepository = candidateRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.electionPartyRepository = electionPartyRepository;
        this.electionCandidateRepository = electionCandidateRepository;
    }

    @GetMapping("/elections-list")
    public String index(HttpSession session, Model model) {
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            model.addAttribute("page_details", new PageDetails("EVS | Election", "Elections"));
            model.addAttribute("user", userDetails);
        }
        List<Election> electionList = electionRepository.findAll(Status.LIVE);
        List<com.nipun.election.models.responseModels.Election> elections = new ArrayList<>();
        for (Election e : electionList) {
            User startedBy = null;
            User endedBy = null;
            com.nipun.election.models.responseModels.Election election = new com.nipun.election.models.responseModels.Election(e.getId(), e.getName(), e.getYear(), e.getMaxSeats(), e.getStartTime(), startedBy, e.getStartedTime(), e.getEndTime(), endedBy, e.getEndedTime(), e.getVotes(), e.getVotesValid(), e.getVotesInvalid(), e.getElectionStatus());
            elections.add(election);
        }
        model.addAttribute(ModelAttributes.ELECTIONS, elections);

        //        BEGIN::Adding candidates
        List<Candidate> candidateList = candidateRepository.findAll(Status.LIVE);
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
        //        END::Adding candidates
        return ViewHolder.ELECTIONS_LIST_VIEW;
    }

    @PostMapping(path = "/election-registration-request", consumes = MediaType.ALL_VALUE)
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, ElectionRegistrationRequest request, RedirectAttributes redirectAttributes) throws ParseException {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        UserDetails userDetails = this.sessionConfigService.extractUserDetails(servletRequest.getSession());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        message.setType(FrontEndAlertType.SUCCESS);
        Election election = null;
        if (request.getFormType() == FormTypes.SAVE) {
            election = new Election();
            election.setCreatedAt(date);
            election.setVotes(0);
            election.setVotesValid(0);
            election.setVotesInvalid(0);
            message.setMessage("Election has been saved successfully.");
        } else {
            election = this.electionRepository.getById(request.getFormId());
            message.setMessage("Edited election details have been saved successfully.");
        }
        election.setElectionStatus(ElectionStatus.START);
        election.setName(request.getName());
        election.setYear(request.getYear());
        election.setMaxSeats(request.getMaxSeats());
        election.setStartTime(sdf.parse(request.getStartTime()));
        election.setEndTime(sdf.parse(request.getEndTime()));
        election.setUpdatedAt(date);
        election.setStatus(Status.LIVE);
        election.setCreatedBy(userDetails.getId());
        electionRepository.saveAndFlush(election);
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

    @PostMapping(path = "/election-delete", consumes = MediaType.ALL_VALUE)
    public RedirectView deleteElection(HttpServletRequest servletRequest, ElectionDeleteRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Election election = this.electionRepository.getById(request.getId());
        if (election != null) {
            election.setUpdatedAt(date);
            election.setStatus(Status.DELETE);
            this.electionRepository.saveAndFlush(election);
            message.setType(FrontEndAlertType.SUCCESS);
            message.setMessage("Election deletion has successful!");
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

    @PostMapping(path = "/election-change-status", consumes = MediaType.ALL_VALUE)
    public RedirectView changeElectionStatus(HttpServletRequest servletRequest, ElectionChangeStatusRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Election election = this.electionRepository.getById(request.getId());
        if (election != null) {
            election.setUpdatedAt(date);
            election.setElectionStatus(request.getStatus());
            this.electionRepository.saveAndFlush(election);
            message.setType(FrontEndAlertType.SUCCESS);
            if (request.getStatus() == ElectionStatus.IN_PROGRESS) {
                message.setMessage("Election has started!");
            } else if (request.getStatus() == ElectionStatus.END) {
                message.setMessage("Election has ended!");
            }
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

    @PostMapping(path = "/assign-candidate-to-election", consumes = MediaType.ALL_VALUE)
    public RedirectView addCandidateToElection(HttpServletRequest servletRequest, ElectionAddCandidateRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Election election = this.electionRepository.getById(request.getElectionId());
        if (election != null) {
            ElectionCandidate electionCandidate = this.electionCandidateRepository.getByElectionAndCandidate(election.getId(), request.getCandidateId(), Status.LIVE);
            if (electionCandidate==null){
                ElectionCandidate ec = new ElectionCandidate();
                ec.setElectionId(election.getId());
                ec.setCandidateId(request.getCandidateId());
                ec.setSeatType(SeatType.CANDIDATE);
                ec.setIsLeader(0);
                ec.setVotes(0);
                ec.setCreatedAt(date);
                ec.setUpdatedAt(date);
                ec.setStatus(Status.LIVE);
                this.electionCandidateRepository.save(ec);
                message.setMessage("Candidate has added to the election successfully!");
                message.setType(FrontEndAlertType.SUCCESS);
            }else{
                message.setMessage("This candidate has already added to this election!");
                message.setType(FrontEndAlertType.INFO);
            }
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

    @PostMapping(path = "/assign-all-candidates-to-election", consumes = MediaType.ALL_VALUE)
    public RedirectView addAllCandidatesToElection(HttpServletRequest servletRequest, ElectionAddAllCandidatesRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Election election = this.electionRepository.getById(request.getElectionId());
        if (election != null) {
            List<Candidate> candidates = this.candidateRepository.findAll(Status.LIVE);
            for (Candidate candidate:candidates){
                ElectionCandidate electionCandidate = this.electionCandidateRepository.getByElectionAndCandidate(election.getId(), candidate.getId(), Status.LIVE);
                if (electionCandidate==null){
                    ElectionCandidate ec = new ElectionCandidate();
                    ec.setElectionId(election.getId());
                    ec.setCandidateId(candidate.getId());
                    ec.setSeatType(SeatType.CANDIDATE);
                    ec.setIsLeader(0);
                    ec.setVotes(0);
                    ec.setCreatedAt(date);
                    ec.setUpdatedAt(date);
                    ec.setStatus(Status.LIVE);
                    this.electionCandidateRepository.save(ec);
                    message.setMessage("Candidate has added to the election successfully!");
                    message.setType(FrontEndAlertType.SUCCESS);
                }
            }
            message.setMessage("All candidates have added to the election successfully!");
            message.setType(FrontEndAlertType.SUCCESS);
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

}
