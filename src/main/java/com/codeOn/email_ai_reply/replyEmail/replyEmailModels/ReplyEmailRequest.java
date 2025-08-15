package com.codeOn.email_ai_reply.replyEmail.replyEmailModels;

import lombok.Data;

@Data
public class ReplyEmailRequest {
    String emailBody;
    String tone;
    String subject;
    String sendersName;
}
