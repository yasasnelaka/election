package com.lk.election.controllers.api;

import com.lk.election.dbTier.entities.Citizen;
import com.lk.election.dbTier.repositories.CitizenRepository;
import com.lk.election.init.MessageBrokerResponseType;
import com.lk.election.init.URLHolder;
import com.lk.election.models.requestModels.FingerPrintRequest;
import com.lk.election.models.responseModels.MessageBrokerResponse;
import com.lk.election.notification.WSService;
import com.lk.election.init.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class FingerPrintAuthenticationController {

    private final WSService webSocket;
    private final CitizenRepository citizenRepository;

    @Autowired
    public FingerPrintAuthenticationController(WSService webSocket,CitizenRepository citizenRepository){
        this.webSocket = webSocket;
        this.citizenRepository = citizenRepository;
    }

    @RequestMapping(value = "/finger-print-auth",method = RequestMethod.GET)
    public ResponseEntity<?> fingerPrintAuth(FingerPrintRequest request){
        MessageBrokerResponse response = new MessageBrokerResponse();

        Citizen citizen = citizenRepository.getByFingerprint(request.getFingerPrint(), Status.LIVE);
        if (citizen==null){
            response.setCanVote(false);
            response.setType(MessageBrokerResponseType.NOTIFICATION);
            response.setMessage("You can't vote for this election!");
        }else{
            response.setCanVote(true);
            response.setType(MessageBrokerResponseType.URL);
            response.setMessage(URLHolder.VOTING_FORM_VIEW+"?fingerprintId="+citizen.getFingerprintId());
        }

        webSocket.notifyFrontend(response);
        return ResponseEntity.ok("OK");
    }
}