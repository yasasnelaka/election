package com.nipun.election.controllers.web;

import com.nipun.election.dbTier.entities.Citizen;
import com.nipun.election.dbTier.entities.District;
import com.nipun.election.dbTier.entities.PollingDivision;
import com.nipun.election.dbTier.entities.Province;
import com.nipun.election.dbTier.repositories.CitizenRepository;
import com.nipun.election.dbTier.repositories.DistrictRepository;
import com.nipun.election.dbTier.repositories.PollingDivisionRepository;
import com.nipun.election.dbTier.repositories.ProvinceRepository;
import com.nipun.election.init.*;
import com.nipun.election.models.requestModels.CitizenRegistrationRequest;
import com.nipun.election.models.requestModels.LoginRequest;
import com.nipun.election.models.responseModels.AlertMessage;
import com.nipun.election.models.responseModels.PageDetails;
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
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, CitizenRegistrationRequest request, RedirectAttributes redirectAttributes) throws ParseException {
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        message.setType(FrontEndAlertType.SUCCESS);
        if (request.getFormType()== FormTypes.SAVE){
            System.out.println("============================");
            System.out.println(request.toString());
            System.out.println("============================");
            Citizen citizen = new Citizen();
            Date date = new Date();
            citizen.setPollingDivisionId(request.getPollingDivision());
            citizen.setNic(request.getNic());
            citizen.setFullName(request.getFullName());
            citizen.setGender(request.getGender());
            citizen.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthday()));
            citizen.setEmail(request.getEmail());
            citizen.setMobile(request.getMobile());
            citizen.setAddress(request.getAddress());
            citizen.setCreatedAt(date);
            citizen.setUpdatedAt(date);
            citizen.setStatus(Status.LIVE);
            this.citizenRepository.saveAndFlush(citizen);
            message.setMessage("Citizen has been saved successfully.");
            redirectAttributes.addFlashAttribute(ModelAttributes.ALERT,message);
        }else{

        }
        return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
    }
}
