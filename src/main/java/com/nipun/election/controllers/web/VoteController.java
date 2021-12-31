package com.nipun.election.controllers.web;

import com.nipun.election.models.responseModels.PageDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoteController {
    @GetMapping("/voting-form")
    public String index(Model model){
        model.addAttribute("page_details", new PageDetails("EVS | Voting Form", "Voting Form"));
        return "voting-form";
    }
}
