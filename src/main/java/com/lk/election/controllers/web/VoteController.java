package com.lk.election.controllers.web;

import com.lk.election.dbTier.entities.*;
import com.lk.election.dbTier.repositories.*;
import com.lk.election.init.*;
import com.lk.election.models.requestModels.VoteRequest;
import com.lk.election.models.responseModels.AlertMessage;
import com.lk.election.models.responseModels.ElectionCandidate;
import com.lk.election.models.responseModels.PageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class VoteController {

    private final ElectionRepository electionRepository;
    private final ElectionCandidateRepository electionCandidateRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionPartyRepository electionPartyRepository;
    private final PollingDivisionRepository pollingDivisionRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public VoteController(ElectionRepository electionRepository, ElectionCandidateRepository electionCandidateRepository, CandidateRepository candidateRepository,
                          ElectionPartyRepository electionPartyRepository,
                          PollingDivisionRepository pollingDivisionRepository,
                          DistrictRepository districtRepository,
                          ProvinceRepository provinceRepository) {
        this.electionRepository = electionRepository;
        this.electionCandidateRepository = electionCandidateRepository;
        this.candidateRepository = candidateRepository;
        this.electionPartyRepository = electionPartyRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("/voting-form")
    public String index(Model model) {
        model.addAttribute("page_details", new PageDetails("EVS | Voting Form", "Voting Form"));
        List<ElectionCandidate> electionCandidateList = new ArrayList<>();
        Election election = this.electionRepository.getOneByElectionStatus(ElectionStatus.IN_PROGRESS, Status.LIVE);
        if (election != null) {
            model.addAttribute(ModelAttributes.ELECTION,        new com.lk.election.models.responseModels.Election().convertEntityToResponseModel(election,null,null));
            model.addAttribute("page_details", new PageDetails("EVS | Voting Form", election.getName()+" ("+election.getYear()+")"));
            List<com.lk.election.dbTier.entities.ElectionCandidate> allByElection = electionCandidateRepository.getAllByElection(election.getId(), Status.LIVE);
            for (com.lk.election.dbTier.entities.ElectionCandidate ec : allByElection) {
                Candidate candidate = candidateRepository.getById(ec.getCandidateId());
                com.lk.election.dbTier.entities.PollingDivision pd = this.pollingDivisionRepository.getById(candidate.getPollingDivisionId());
                District d = districtRepository.getById(pd.getDistrictId());
                Province p = this.provinceRepository.getById(d.getProvinceId());
                com.lk.election.models.responseModels.PollingDivision pollingDivision = new com.lk.election.models.responseModels.PollingDivision().convertEntityToResponseModel(pd, d, p);
                com.lk.election.models.responseModels.ElectionParty electionParty = new com.lk.election.models.responseModels.ElectionParty().convertEntityToResponseModel(this.electionPartyRepository.getById(candidate.getElectionPartyId()));

                com.lk.election.models.responseModels.Candidate cd = new com.lk.election.models.responseModels.Candidate().convertEntityToResponseModel(candidate, electionParty, pollingDivision);
                ElectionCandidate electionCandidate = new ElectionCandidate().convertEntityToResponseModel(ec, cd);
                electionCandidateList.add(electionCandidate);
            }
            model.addAttribute(ModelAttributes.ELECTION_CANDIDATES, electionCandidateList);
        } else {

        }

        return ViewHolder.VOTING_FORM;
    }

    @PostMapping(path = "/vote-request", consumes = MediaType.ALL_VALUE)
    public RedirectView addCandidateToElection(HttpServletRequest servletRequest, VoteRequest request, RedirectAttributes redirectAttributes) {
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        if ((request.getElectionCandidates()==null) || (request.getElectionCandidates().size()>3)){
            redirectAttributes.addFlashAttribute(ModelAttributes.ALERT,new AlertMessage("Alert","You can only select 3 maximum candidates!",FrontEndAlertType.WARNING,true));
            return new RedirectView(URLHolder.VOTING_FORM_VIEW);
        }
        List<Integer> electionCandidates = request.getElectionCandidates();
        for (int cd:electionCandidates){
            com.lk.election.dbTier.entities.ElectionCandidate electionCandidate = this.electionCandidateRepository.getById(cd);
            if (electionCandidate!=null){
                electionCandidate.setVotes(electionCandidate.getVotes()+1);
                electionCandidate.setUpdatedAt(date);
                electionCandidateRepository.saveAndFlush(electionCandidate);
            }
        }
        return new RedirectView(URLHolder.VOTING_FORM_VIEW);
    }
}
