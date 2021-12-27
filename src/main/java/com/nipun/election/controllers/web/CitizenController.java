package com.nipun.election.controllers.web;

import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.viewsModels.PageDetails;
import com.nipun.election.models.viewsModels.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CitizenController {

    @GetMapping("/citizen-list")
    public String index(Model model){
        model.addAttribute("page_details",new PageDetails("EVS | Citizens","Citizens"));
        model.addAttribute("user",new UserDetails(1,"John Doe","GN Officer"));
        return ViewHolder.CITIZEN_LIST_VIEW;
    }
}
