package com.nipun.election.controllers.web.auth;

import com.nipun.election.dbTier.entities.User;
import com.nipun.election.dbTier.repositories.UserRepository;
import com.nipun.election.init.*;
import com.nipun.election.models.requestModels.LoginRequest;
import com.nipun.election.models.responseModels.AlertMessage;
import com.nipun.election.services.SessionConfigService;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionConfigService sessionConfigService;

    public LoginController(SessionConfigService sessionConfigService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.sessionConfigService = sessionConfigService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String index() {
        return ViewHolder.LOGIN_VIEW;
    }

    @PostMapping(path = "/login-request", consumes = MediaType.ALL_VALUE)
    public RedirectView loginRequest(HttpServletRequest servletRequest, LoginRequest request, RedirectAttributes redirectAttributes) {
        AlertMessage message = new AlertMessage();
        message.setShow(true);
        message.setType(FrontEndAlertType.WARNING);
        message.setHeader("Alert");
        Date date = new Date();
        //Validating
        User user = userRepository.getByEmail(request.getEmail(), Status.LIVE);
        if (user == null) {
            message.setMessage("Email or password has not been matched! Please try again with correct credentials.");
            redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }else if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            message.setMessage("Password has not been matched! Please try again with correct credentials.");
            redirectAttributes.addFlashAttribute(ModelAttributes.ALERT, message);
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }else{
            sessionConfigService.configAuthSession(user,servletRequest);
            if (user.getUserTypeId()==UserTypes.DEC){
                return new RedirectView(URLHolder.ELECTIONS_LIST_VIEW);
            }else  if (user.getUserTypeId()==UserTypes.GNO){
                return new RedirectView(URLHolder.CITIZEN_LIST_VIEW);
            }
//            redirectAttributes.addFlashAttribute(ModelAttributes.ALERT,new AlertMessage("Message",new com.nipun.election.models.responseModels.User().convertEntityToResponseModel(user).toString(),FrontEndAlertType.INFO,true));
            return new RedirectView(URLHolder.LOGIN_VIEW);
        }
    }

}
