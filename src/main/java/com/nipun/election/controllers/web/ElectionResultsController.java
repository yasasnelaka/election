package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.Election;
import com.nipun.election.dbTier.entities.ElectionParty;
import com.nipun.election.dbTier.repositories.CitizenRepository;
import com.nipun.election.dbTier.repositories.ElectionCandidateRepository;
import com.nipun.election.dbTier.repositories.ElectionPartyRepository;
import com.nipun.election.dbTier.repositories.ElectionRepository;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.Status;
import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.responseModels.ElectionPartyResult;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.UserDetails;
import com.nipun.election.services.SessionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ElectionResultsController {

    private final SessionConfigService sessionConfigService;
    private final ElectionRepository electionRepository;
    private final ElectionCandidateRepository electionCandidateRepository;
    private final ElectionPartyRepository electionPartyRepository;
    private final CitizenRepository citizenRepository;

    @Autowired
    public ElectionResultsController(SessionConfigService sessionConfigService,ElectionRepository electionRepository,ElectionCandidateRepository electionCandidateRepository,
                                     ElectionPartyRepository electionPartyRepository,CitizenRepository citizenRepository){
        this.sessionConfigService = sessionConfigService;
        this.electionRepository = electionRepository;
        this.electionCandidateRepository = electionCandidateRepository;
        this.electionPartyRepository = electionPartyRepository;
        this.citizenRepository = citizenRepository;
    }

    @GetMapping("/election-results")
    public String index(HttpSession session, Model model, @RequestParam("id") int id){
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            Election election = this.electionRepository.getById(id);
            model.addAttribute("page_details",new PageDetails("EVS | Election",election.getName()+" - "+election.getYear()));
            model.addAttribute("user", userDetails);
            List<ElectionPartyResult> partyResults = new ArrayList<>();

//            BEGIN::Getting party wise data
            List<ElectionParty> parties = this.electionPartyRepository.findAllByElection(election.getId(), Status.LIVE);
            int citizenTot = this.citizenRepository.findAll(Status.LIVE).size();
            Integer totalVotesByElection = this.electionCandidateRepository.getTotalVotesByElection(election.getId(), Status.LIVE);
            if (totalVotesByElection==null){
                totalVotesByElection=0;
            }
            for (ElectionParty party:parties){
                if (party!=null){
                    Integer totalVotesByElectionAndParty = this.electionCandidateRepository.getTotalVotesByElectionAndParty(election.getId(), party.getId(), Status.LIVE);
                    if (totalVotesByElectionAndParty==null){
                        totalVotesByElectionAndParty = 0;
                    }
                    double i = totalVotesByElectionAndParty;
                    double j = totalVotesByElection;
                    double percentage = i/j*100;
                    System.out.println("=============================");
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(percentage);
                    System.out.println("=============================");
                    int seats = (int) (election.getMaxSeats()*percentage/100);
                    partyResults.add(new ElectionPartyResult(party.getId(),party.getName(),party.getAbbreviation(),party.getThemeColour(),totalVotesByElectionAndParty,seats,percentage));
                }
            }
            model.addAttribute(ModelAttributes.ELECTION_PARTIES,partyResults);
//            END::Getting party wise data


            return ViewHolder.ELECTION_RESULTS_VIEW;
        }
    }
}
