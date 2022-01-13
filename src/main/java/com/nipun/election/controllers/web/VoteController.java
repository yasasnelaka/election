package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.*;
import com.nipun.election.dbTier.repositories.*;
import com.nipun.election.init.ElectionStatus;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.Status;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.PollingDivision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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
        List<com.nipun.election.models.responseModels.ElectionCandidate> electionCandidateList = new ArrayList<>();
        Election election = this.electionRepository.getOneByElectionStatus(ElectionStatus.IN_PROGRESS, Status.LIVE);
        if (election != null) {
            List<ElectionCandidate> allByElection = electionCandidateRepository.getAllByElection(election.getId(), Status.LIVE);
            for (ElectionCandidate ec : allByElection) {
                Candidate candidate = candidateRepository.getById(ec.getCandidateId());
                com.nipun.election.dbTier.entities.PollingDivision pd = this.pollingDivisionRepository.getById(candidate.getPollingDivisionId());
                District d = districtRepository.getById(pd.getDistrictId());
                Province p = this.provinceRepository.getById(d.getProvinceId());
                PollingDivision pollingDivision = new PollingDivision().convertEntityToResponseModel(pd, d, p);
                com.nipun.election.models.responseModels.ElectionParty electionParty = new com.nipun.election.models.responseModels.ElectionParty().convertEntityToResponseModel(this.electionPartyRepository.getById(candidate.getElectionPartyId()));

                com.nipun.election.models.responseModels.Candidate cd = new com.nipun.election.models.responseModels.Candidate().convertEntityToResponseModel(candidate, electionParty, pollingDivision);
                com.nipun.election.models.responseModels.ElectionCandidate electionCandidate = new com.nipun.election.models.responseModels.ElectionCandidate().convertEntityToResponseModel(ec, cd);
                electionCandidateList.add(electionCandidate);
            }
            model.addAttribute(ModelAttributes.ELECTION_CANDIDATES, electionCandidateList);
        } else {

        }

        return ViewHolder.VOTING_FORM;
    }
}
