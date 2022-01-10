package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.Citizen;
import com.nipun.election.dbTier.entities.District;
import com.nipun.election.dbTier.entities.PollingDivision;
import com.nipun.election.dbTier.entities.Province;
import com.nipun.election.dbTier.repositories.CitizenRepository;
import com.nipun.election.dbTier.repositories.DistrictRepository;
import com.nipun.election.dbTier.repositories.PollingDivisionRepository;
import com.nipun.election.dbTier.repositories.ProvinceRepository;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.Status;
import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.CitizenRegistrationRequest;
import com.nipun.election.models.requestModels.LoginRequest;
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
import java.util.List;

@Controller
public class CitizenController {

    private final SessionConfigService sessionConfigService;
    private final CitizenRepository citizenRepository;
    private final PollingDivisionRepository pollingDivisionRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    public CitizenController(SessionConfigService sessionConfigService, CitizenRepository citizenRepository, PollingDivisionRepository pollingDivisionRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
        this.sessionConfigService = sessionConfigService;
        this.citizenRepository = citizenRepository;
        this.pollingDivisionRepository = pollingDivisionRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("/citizen-list")
    public String index(HttpSession session, Model model) {
        if (!this.sessionConfigService.isUserValid(session)) {
            return "redirect:" + URLHolder.LOGIN_VIEW;
        } else {
            UserDetails userDetails = sessionConfigService.extractUserDetails(session);
            model.addAttribute(ModelAttributes.USER, userDetails);
            model.addAttribute("page_details", new PageDetails("EVS | Citizens", "Citizens"));
            model.addAttribute("user", userDetails);
        }
        List<Citizen> citizensFromDb = citizenRepository.findAll(Status.LIVE);
        List<com.nipun.election.models.responseModels.Citizen> citizens = new ArrayList<>();
        for (Citizen citizen : citizensFromDb) {
            PollingDivision pollingDivision = pollingDivisionRepository.getById(citizen.getPollingDivisionId());
            District district = districtRepository.getById(pollingDivision.getDistrictId());
            Province province = provinceRepository.getById(district.getId());
            com.nipun.election.models.responseModels.PollingDivision pollingDivision1 = new com.nipun.election.models.responseModels.PollingDivision().convertEntityToResponseModel(pollingDivision, district, province);
            citizens.add(new com.nipun.election.models.responseModels.Citizen().convertEntityToResponseModel(citizen, pollingDivision1));
        }
        model.addAttribute(ModelAttributes.CITIZENS, citizens);
        return ViewHolder.CITIZEN_LIST_VIEW;
    }

    @PostMapping(path = "/citizen-registration-request", consumes = MediaType.ALL_VALUE)
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, CitizenRegistrationRequest request, Model model) {
//TODO::Implement the business process for citizenRegistration
        return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
    }
}
