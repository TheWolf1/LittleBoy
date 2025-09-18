package com.little_boy.little_boy.Infrastructure.Services;

import com.little_boy.little_boy.Application.Services.INotificationService;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

public class WhatsappService implements INotificationService {


    private final RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://graph.facebook.com/v22.0/";
    private final String phoneNumberId = "629946340212570";
    private final String token = "EAAKVBsiN5foBPHjeA05hzXoFXG7UucxaLW6ln7BD16rIg5lmdkYOIUC6L9b7MridRzRgAlZB8ZCWsjurFbXNKeQE5wAbiFpAXduRlvZBeo4hMvgO9mNMiGKF7WUPyVeYyeSEdFZBRVdnh9KZASvtWilKmGHAJYrQvFOnY6jebnhry6ud6iFq5O2f5RVqfbcrizQZDZD";
    private final String to = "34603338214";

    @Override
    public void sendNotification(String message) {
        String endpoint = url + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "template",
          "template": {
            "name": "hello_world",
            "language": { "code": "en_US" }
          }
        }
        """.formatted(to);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        String response = restTemplate.postForObject(endpoint, request, String.class);

        System.out.println("Respuesta de WhatsApp: " + response);
    }
}
