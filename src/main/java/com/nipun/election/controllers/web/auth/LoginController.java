package com.nipun.election.controllers.web.auth;

import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.LoginRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String index(){
        return ViewHolder.LOGIN_VIEW;
    }

    @PostMapping(path = "/login-request",consumes = MediaType.ALL_VALUE)
    public RedirectView loginRequest(HttpServletRequest servletRequest, LoginRequest request, Model model){
        //TODO:Business Process for Login
        return new RedirectView(URLHolder.LOGIN_VIEW);
    }

}
