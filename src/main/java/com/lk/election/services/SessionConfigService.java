package com.lk.election.services;

import com.lk.election.dbTier.entities.User;
import com.lk.election.models.responseModels.UserDetails;
import com.lk.election.models.sessionModels.SessionUser;
import com.lk.election.init.SessionAttributes;
import com.lk.election.init.UserTypes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SessionConfigService {

    public void configAuthSession(User user, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(user.getId());
        sessionUser.setFirstName(user.getFirstName());
        sessionUser.setLastName(user.getLastName());
        sessionUser.setEmail(user.getEmail());
        sessionUser.setGender(user.getGender().intValue());
        sessionUser.setMobile(user.getMobile());
        sessionUser.setUserTypeId(user.getUserTypeId());
        sessionUser.setAddress(user.getAddress());
        session.setAttribute(SessionAttributes.USER, sessionUser);
    }

    public boolean isUserValid(HttpSession session){
        return session.getAttribute(SessionAttributes.USER)!=null;
    }

    public UserDetails extractUserDetails(HttpSession httpSession) {
        SessionUser user = (SessionUser) httpSession.getAttribute(SessionAttributes.USER);
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(user.getEmail());
        userDetails.setId(user.getId());
        userDetails.setName(user.getName());
        userDetails.setTypeId(user.getUserTypeId());
        userDetails.setType(UserTypes.ALL_TYPES_MIDDLE[user.getUserTypeId()]);
        return userDetails;
    }

//    public String extractUserType(HttpSession session){
//        Object attribute = session.getAttribute(SessionAttributes.USER_TYPE);
//        return attribute==null?null:attribute.toString();
//    }
}