package com.lk.election.controllers.web.auth;

import com.lk.election.init.URLHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class LogOutController {

    @PostMapping(path = "/sign-out")
    public RedirectView logOutRequest(HttpSession session) {
        session.invalidate();
        return new RedirectView(URLHolder.LOGIN_VIEW);
    }

}
