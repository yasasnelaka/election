//package com.nipun.election.controllers.api;
//
//import com.nipun.election.config.Config;
//import com.nipun.election.dbTier.entities.User;
//import com.nipun.election.dbTier.repositories.UserRepository;
//import com.nipun.election.init.Status;
//import com.nipun.election.models.requestModels.UserRegistrationRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.Date;
//
//@RequestMapping("/api")
//@Controller
//public class UserRegistrationController {
//
//    private final UserRepository userRepository;
//    private final Config config;
//
//    @Autowired
//    public UserRegistrationController(UserRepository userRepository, Config config) {
//        this.userRepository = userRepository;
//        this.config = config;
//    }
//
//    @RequestMapping(value = "/create-user", method = RequestMethod.GET)
//    public ResponseEntity<?> createUser(UserRegistrationRequest request) {
//        User user = new User();
//        Date date = new Date();
//        user.setUserTypeId(request.getUserType());
//        user.setEmail(request.getEmail());
//        user.setPassword(config.encoder().encode(request.getPassword()));
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setGender((byte) request.getGender());
//        user.setMobile(request.getMobile());
//        user.setAddress(request.getAddress());
//        user.setCreatedAt(date);
//        user.setUpdatedAt(date);
//        user.setStatus(Status.LIVE);
//        userRepository.saveAndFlush(user);
//        return ResponseEntity.ok(new com.nipun.election.models.responseModels.User().convertEntityToResponseModel(user));
//    }
//}
