package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.*;
import com.nipun.election.dbTier.repositories.*;
import com.nipun.election.init.*;
import com.nipun.election.models.responseModels.CandidateFinalResult;
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
    private final CandidateRepository candidateRepository;
    private final PollingDivisionRepository pollingDivisionRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ElectionResultsController(SessionConfigService sessionConfigService, ElectionRepository electionRepository, ElectionCandidateRepository electionCandidateRepository,
                                     ElectionPartyRepository electionPartyRepository, CitizenRepository citizenRepository, CandidateRepository candidateRepository,
                                     PollingDivisionRepository pollingDivisionRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
        this.sessionConfigService = sessionConfigService;
        this.electionRepository = electionRepository;
        this.electionCandidateRepository = electionCandidateRepository;
        this.electionPartyRepository = electionPartyRepository;
        this.citizenRepository = citizenRepository;
        this.candidateRepository = candidateRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("/election-results")
    public String index(HttpSession session, Model model, @RequestParam("id") int id) {
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            Election election = this.electionRepository.getById(id);
            model.addAttribute("page_details", new PageDetails("EVS | Election", election.getName() + " - " + election.getYear()));
            model.addAttribute("user", userDetails);
            List<ElectionPartyResult> partyResults = new ArrayList<>();

//            BEGIN::Getting party wise data
            List<ElectionParty> parties = this.electionPartyRepository.findAllByElection(election.getId(), Status.LIVE);
            int citizenTot = this.citizenRepository.findAll(Status.LIVE).size();
            Integer totalVotesByElection = this.electionCandidateRepository.getTotalVotesByElection(election.getId(), Status.LIVE);
            if (totalVotesByElection == null) {
                totalVotesByElection = 0;
            }
            for (ElectionParty party : parties) {
                if (party != null) {
                    Integer totalVotesByElectionAndParty = this.electionCandidateRepository.getTotalVotesByElectionAndParty(election.getId(), party.getId(), Status.LIVE);
                    if (totalVotesByElectionAndParty == null) {
                        totalVotesByElectionAndParty = 0;
                    }
                    double i = totalVotesByElectionAndParty;
                    double j = totalVotesByElection;
                    double percentage = i / j * 100;
                    System.out.println("=============================");
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(percentage);
                    System.out.println("=============================");
                    int seats = (int) (election.getMaxSeats() * percentage / 100);
                    partyResults.add(new ElectionPartyResult(party.getId(), party.getName(), party.getAbbreviation(), party.getThemeColour(), totalVotesByElectionAndParty, seats, percentage));
                }
            }
            model.addAttribute(ModelAttributes.ELECTION_PARTIES, partyResults);
//            END::Getting party wise data

            List<ElectionCandidate> electionCandidateList = this.electionCandidateRepository.getAllByElection(election.getId(), Status.LIVE);
            List<CandidateFinalResult> candidateFinalResults = new ArrayList<>();
            for (ElectionCandidate ec : electionCandidateList) {
                Candidate candidate = this.candidateRepository.getById(ec.getCandidateId());
                PollingDivision division = this.pollingDivisionRepository.getById(candidate.getPollingDivisionId());
                District district = this.districtRepository.getById(division.getDistrictId());
                Province province = this.provinceRepository.getById(district.getProvinceId());
                ElectionParty party = this.electionPartyRepository.getById(candidate.getElectionPartyId());
                String partyName = party.getName() + " (" + party.getAbbreviation() + ")";
                int isSelected = 0;
                //TODO::Check is selected
                CandidateFinalResult cc = new CandidateFinalResult(candidate.getId(), province.getProvince(), district.getDistrict(), division.getPollingDivision(), partyName, candidate.getElectionNumber(), candidate.getFullName(), Gender.ALL[candidate.getGender()], ec.getVotes(), isSelected);
                candidateFinalResults.add(cc);
            }
            model.addAttribute(ModelAttributes.CANDIDATES, candidateFinalResults);

            return ViewHolder.ELECTION_RESULTS_VIEW;
        }
    }
}
