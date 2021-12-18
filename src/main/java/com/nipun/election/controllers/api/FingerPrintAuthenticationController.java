package com.nipun.election.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class FingerPrintAuthenticationController {

    @RequestMapping(value = "/finger-print-auth",method = RequestMethod.GET)
    public ResponseEntity<?> fingerPrintAuth(){
        return ResponseEntity.ok("OK");
    }
}