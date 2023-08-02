package com.lk.election.controllers.web;

import com.lk.election.dbTier.entities.Citizen;
import com.lk.election.dbTier.entities.District;
import com.lk.election.dbTier.entities.PollingDivision;
import com.lk.election.dbTier.entities.Province;
import com.lk.election.dbTier.repositories.CitizenRepository;
import com.lk.election.dbTier.repositories.DistrictRepository;
import com.lk.election.dbTier.repositories.PollingDivisionRepository;
import com.lk.election.dbTier.repositories.ProvinceRepository;
import com.lk.election.init.*;
import com.lk.election.models.requestModels.CitizenDeleteRequest;
import com.lk.election.models.requestModels.CitizenRegistrationRequest;
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

        List<Citizen> citizensFromDb = citizenRepository.findAll(Status.LIVE);
        List<com.lk.election.models.responseModels.Citizen> citizens = new ArrayList<>();
        for (Citizen citizen : citizensFromDb) {
            PollingDivision pollingDivision = pollingDivisionRepository.getById(citizen.getPollingDivisionId());
            District district = districtRepository.getById(pollingDivision.getDistrictId());
            Province province = provinceRepository.getById(district.getId());
            com.lk.election.models.responseModels.PollingDivision pollingDivision1 = new com.lk.election.models.responseModels.PollingDivision().convertEntityToResponseModel(pollingDivision, district, province);
            citizens.add(new com.lk.election.models.responseModels.Citizen().convertEntityToResponseModel(citizen, pollingDivision1));
        }
        model.addAttribute(ModelAttributes.CITIZENS, citizens);
        return ViewHolder.CITIZEN_LIST_VIEW;
    }

    @PostMapping(path = "/citizen-registration-request", consumes = MediaType.ALL_VALUE)
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, CitizenRegistrationRequest request, RedirectAttributes redirectAttributes) throws ParseException {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        message.setType(FrontEndAlertType.SUCCESS);
        Citizen citizen = null;
        if (request.getFormType() == FormTypes.SAVE) {
            citizen = new Citizen();
            citizen.setCreatedAt(date);
            message.setMessage("Citizen has been saved successfully.");
        } else {
            citizen = citizenRepository.getById(request.getFormId());
            message.setMessage("Edited citizen details have been saved successfully.");
        }
        citizen.setFingerprintId(request.getFingerprintId());
        citizen.setPollingDivisionId(request.getPollingDivision());
        citizen.setNic(request.getNic());
        citizen.setFullName(request.getFullName());
        citizen.setGender(request.getGender());
        citizen.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthday()));
        citizen.setEmail(request.getEmail());
        citizen.setMobile(request.getMobile());
        citizen.setAddress(request.getAddress());
        citizen.setUpdatedAt(date);
        citizen.setStatus(Status.LIVE);
        this.citizenRepository.saveAndFlush(citizen);
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
    }

    @PostMapping(path = "/citizen-delete", consumes = MediaType.ALL_VALUE)
    public RedirectView deleteBankUser(HttpServletRequest servletRequest, CitizenDeleteRequest request, RedirectAttributes redirectAttributes) {
        if (!this.sessionConfigService.isUserValid(servletRequest.getSession())) {
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
        Date date = new Date();
        AlertMessage message = new AlertMessage();
        message.setHeader("Message");
        message.setShow(true);
        Citizen citizen = citizenRepository.getById(request.getId());
        if (citizen != null) {
            citizen.setUpdatedAt(date);
            citizen.setStatus(Status.DELETE);
            citizenRepository.saveAndFlush(citizen);
            message.setType(FrontEndAlertType.SUCCESS);
            message.setMessage("Citizen Deletion has successful!");
        } else {
            message.setType(FrontEndAlertType.ERROR);
            message.setMessage("Something went wrong! Please try again.");
        }
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
        return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
    }


}
