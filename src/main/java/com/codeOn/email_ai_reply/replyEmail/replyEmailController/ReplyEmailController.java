package com.codeOn.email_ai_reply.replyEmail.replyEmailController;

import com.codeOn.email_ai_reply.replyEmail.replyEmailModels.ResponseDTO;
import com.codeOn.email_ai_reply.replyEmail.replyEmailModels.ReplyEmailRequest;
import com.codeOn.email_ai_reply.replyEmail.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@RestController
@RequestMapping("replyEmail")
public class ReplyEmailController {

    @Autowired
    private EmailServices emailServices;

    @PostMapping("/replyEmailwithAI")
    public ResponseEntity<ResponseDTO> replyEmailwithAI(@RequestBody ReplyEmailRequest req) throws Exception {
        ResponseDTO response = new ResponseDTO();
        response.setEmailReply(emailServices.generateEmailReply(req));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
