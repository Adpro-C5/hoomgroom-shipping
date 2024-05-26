package id.ac.ui.cs.advprog.shipping.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthHelper {
    public String getUserRole(String jwtToken){
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", jwtToken);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "http://34.143.253.15/profile/role",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch(Exception e){
            return null;
        }
    }
}
