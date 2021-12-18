package com.nipun.election.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoteController {
    @GetMapping("voting-form")
    public String index(){
        return "voting-form";
    }
}
