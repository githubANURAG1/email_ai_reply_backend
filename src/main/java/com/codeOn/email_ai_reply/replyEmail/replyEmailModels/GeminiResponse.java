package com.codeOn.email_ai_reply.replyEmail.replyEmailModels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GeminiResponse {
    ArrayList<Candidate> candidates;
    String modelVersion;
    String responseId;
}


