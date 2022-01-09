package com.nipun.election.controllers.api;

import com.nipun.election.models.requestModels.FingerPrintRequest;
import com.nipun.election.notification.WSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class FingerPrintAuthenticationController {

    @Autowired
    public WSService webSocket;

    @RequestMapping(value = "/finger-print-auth",method = RequestMethod.GET)
    public ResponseEntity<?> fingerPrintAuth(FingerPrintRequest request){
        System.out.println("================ "+request.getFingerPrint());
        webSocket.notifyFrontend(request.getFingerPrint());
        return ResponseEntity.ok("OK");
    }
}