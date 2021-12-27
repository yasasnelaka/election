package com.nipun.election.controllers.web;

import com.nipun.election.init.ViewHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitizenController {

    @GetMapping("/citizen-list")
    public String index(){
        return ViewHolder.CITIZEN_LIST_VIEW;
    }
}
