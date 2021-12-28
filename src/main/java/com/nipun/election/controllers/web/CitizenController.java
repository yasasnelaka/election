package com.nipun.election.controllers.web;

import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.CitizenRegistrationRequest;
import com.nipun.election.models.requestModels.LoginRequest;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.UserDetails;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CitizenController {

    @GetMapping("/citizen-list")
    public String index(Model model){
        model.addAttribute("page_details",new PageDetails("EVS | Citizens","Citizens"));
        model.addAttribute("user",new UserDetails(1,"John Doe",2,"GN Officer"));
        return ViewHolder.CITIZEN_LIST_VIEW;
    }

    @PostMapping(path = "/citizen-registration-request",consumes = MediaType.ALL_VALUE)
    public RedirectView citizenRegistration(HttpServletRequest servletRequest, CitizenRegistrationRequest request,Model model){
//TODO::Implement the business process for citizenRegistration
        return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
    }
}
