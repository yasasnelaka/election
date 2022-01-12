package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.Citizen;
import com.nipun.election.dbTier.entities.Election;
import com.nipun.election.dbTier.repositories.ElectionRepository;
import com.nipun.election.dbTier.repositories.UserRepository;
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
    private final UserRepository userRepository;

    public ElectionController(SessionConfigService sessionConfigService, ElectionRepository electionRepository, UserRepository userRepository) {
        this.sessionConfigService = sessionConfigService;
        this.electionRepository = electionRepository;
        this.userRepository = userRepository;
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
            com.nipun.election.models.responseModels.Election election = new com.nipun.election.models.responseModels.Election(e.getId(), e.getName(), e.getYear(), e.getMaxSeats(), e.getStartTime(), startedBy, e.getStartedTime(), e.getEndTime(), endedBy, e.getEndedTime(), e.getVotes(), e.getVotesValid(), e.getVotesInvalid(),e.getElectionStatus());
            elections.add(election);
        }
        model.addAttribute(ModelAttributes.ELECTIONS,elections);
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
            election.setStatus(request.getStatus());
            this.electionRepository.saveAndFlush(election);
            message.setType(FrontEndAlertType.SUCCESS);
            if (request.getStatus()==ElectionStatus.IN_PROGRESS){
                message.setMessage("Election has started!");
            }else if (request.getStatus()==ElectionStatus.END){
                message.setMessage("Election has ended!");
            }
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }

}
