package com.nipun.election.controllers.web.auth;

import com.nipun.election.init.FrontEndAlertType;
import com.nipun.election.init.ModelAttributes;
import com.nipun.election.init.URLHolder;
import com.nipun.election.init.ViewHolder;
import com.nipun.election.models.requestModels.LoginRequest;
import com.nipun.election.models.responseModels.AlertMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String index() {
        return ViewHolder.LOGIN_VIEW;
    }

    @PostMapping(path = "/login-request", consumes = MediaType.ALL_VALUE)
    public RedirectView loginRequest(HttpServletRequest servletRequest, LoginRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, new AlertMessage("Message", request.toString(), FrontEndAlertType.INFO, true));
        return new RedirectView(URLHolder.LOGIN_VIEW);
    }

}
