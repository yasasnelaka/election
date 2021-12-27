package com.nipun.election.controllers.web;

import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitizenController {

    @GetMapping("/citizen-list")
    public String index(Model model){
        model.addAttribute("page_details",new PageDetails("EVS | Citizens","Citizens"));
        model.addAttribute("user",new UserDetails(1,"John Doe",2,"GN Officer"));
        return ViewHolder.CITIZEN_LIST_VIEW;
    }
}
