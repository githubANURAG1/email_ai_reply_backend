package com.codeOn.email_ai_reply.replyEmail.services;

import com.codeOn.email_ai_reply.replyEmail.replyEmailModels.GeminiResponse;
import com.codeOn.email_ai_reply.replyEmail.replyEmailModels.ReplyEmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class EmailServices {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public String generateEmailReply(ReplyEmailRequest req) throws Exception {
        String prompt =framePrompt(req);
        String emailReply = generateEmailReplyUsingGemini(prompt);
        return emailReply;
    }

    private String generateEmailReplyUsingGemini(String prompt) throws Exception {
        //making the reqBody
        Map<String,Map<String,Map<String,String>>> payload = new HashMap<>();
        Map<String,Map<String,String>> contents = new HashMap<>();
        Map<String,String> parts = new HashMap<>();
        String text=prompt;
        parts.put("text",text);
        contents.put("parts",parts);
        payload.put("contents",contents);

        //Uri
        String uri = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        //calling the api
        HttpHeaders headers= new HttpHeaders();
        headers.add("X-goog-api-key","AIzaSyAV7wgrWwbZcTxwonVWmWWg0Z3KpZ8Fe3E");
        headers.add("Content-Type","application/json");
        HttpEntity<Map<String,Map<String,Map<String,String>>>> entity = new HttpEntity<>(payload,headers);
        try{
            String response= restTemplate.exchange(uri, HttpMethod.POST,entity, String.class).getBody();
            GeminiResponse geminiResponse=objectMapper.readValue(response,GeminiResponse.class);
            return geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private String framePrompt(ReplyEmailRequest req) {
        String prompt = "please generate a email reply without subject of the email:"+ req.getEmailBody();
        return prompt;
    }
}
