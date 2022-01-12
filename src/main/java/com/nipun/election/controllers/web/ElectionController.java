package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.Election;
import com.nipun.election.dbTier.repositories.ElectionRepository;
import com.nipun.election.dbTier.repositories.UserRepository;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.CitizenRegistrationRequest;
import com.nipun.election.models.requestModels.ElectionRegistrationRequest;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.User;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ElectionController {

    private final SessionConfigService sessionConfigService;
    private final ElectionRepository electionRepository;
    private final UserRepository userRepository;

    public ElectionController(SessionConfigService sessionConfigService, ElectionRepository electionRepository,UserRepository userRepository){
        this.sessionConfigService = sessionConfigService;
        this.electionRepository = electionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/elections-list")
    public String index(HttpSession session, Model model){
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            model.addAttribute("page_details",new PageDetails("EVS | Election","Elections"));
            model.addAttribute("user", userDetails);
        }
        List<Election> electionList = electionRepository.findAll();
        List<com.nipun.election.models.responseModels.Election> elections = new ArrayList<>();
        for (Election e:electionList){
            User startedBy = null;
            User endedBy = null;
            com.nipun.election.models.responseModels.Election election = new com.nipun.election.models.responseModels.Election(e.getId(),e.getName(),e.getYear(),e.getMaxSeats(),e.getStartTime(),startedBy,e.getStartedTime(),e.getEndTime(),endedBy,e.getEndedTime(),e.getVotes(),e.getVotesValid(),e.getVotesInvalid());
            elections.add(election);
        }
        model.addAttribute(ModelAttributes.ELECTIONS);
        return ViewHolder.ELECTIONS_LIST_VIEW;
    }

    @PostMapping(path = "/election-registration-request",consumes = MediaType.ALL_VALUE)
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, ElectionRegistrationRequest request, Model model){
        System.out.println("===========================================");
//        System.out.println(request.getStartTime());
//        System.out.println(request.getEndTime());
        System.out.println("===========================================");
        //TODO::Implement the business process for citizenRegistration
        return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
    }
}
