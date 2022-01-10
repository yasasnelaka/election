package com.nipun.election.controllers.web;

import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.responseModels.PageDetails;
import com.nipun.election.models.responseModels.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ElectionResultsController {

    @GetMapping("/election-results")
    public String index(Model model){
        model.addAttribute("page_details",new PageDetails("EVS | Election","Parliament Election - 2022"));
        model.addAttribute("user",new UserDetails(1,"John Doe","",1,"DEC Officer"));
        return ViewHolder.ELECTION_RESULTS_VIEW;
    }
}
