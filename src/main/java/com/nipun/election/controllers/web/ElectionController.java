package com.nipun.election.controllers.web;

import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.CitizenRegistrationRequest;
import com.nipun.election.models.requestModels.ElectionRegistrationRequest;
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
public class ElectionController {

    @GetMapping("/elections-list")
    public String index(Model model){
        model.addAttribute("page_details",new PageDetails("EVS | Election","Elections"));
        model.addAttribute("user",new UserDetails(1,"John Doe","",1,"DEC Officer"));
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
