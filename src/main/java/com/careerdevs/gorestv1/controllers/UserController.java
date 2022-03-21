package com.careerdevs.gorestv1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    Environment env;
    
    // http://localhost:4001/api/user/token

    @GetMapping("/token")
    public String token(){
        return env.getProperty("GOREST_TOKEN");
    }

    // GET http://localhost:4001/api/user/{id}
    @GetMapping("/{id}")
    public Object getOneUser (
            @PathVariable("id") String userId,
            RestTemplate restTemplet) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;

            HttpHeaders headers =  new HttpHeaders();
            headers.setBearerAuth(env.getProperty("GOREST_TOKEN"));
//            String token = env.getProperty("GOREST_TOKEN");


//
            HttpEntity request = new HttpEntity(headers);
//            headers.setBearerAuth(token);
            return restTemplet.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Object.class
            );




//            return restTemplet.getForObject(url, Object.class);

        }catch(Exception exception){
                return "404: No user Exists with that ID: " + userId;
            }
        }

        // deleting a user
    // (url/ endpoint) DELETE http://localhost:4001/api/user/{id}

    @DeleteMapping("/{id}")
    public Object deleteOneUser (
            @PathVariable("id") String userId,
            RestTemplate restTemplet) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;

            HttpHeaders headers =  new HttpHeaders();
            headers.setBearerAuth(env.getProperty("GOREST_TOKEN"));
            HttpEntity request = new HttpEntity(headers);


//            ResponseEntity<Object> response =
            restTemplet.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    Object.class);

//            headers.set("Authorization", "Bearer " + token);




//
//            restTemplet.delete(url);
            return "Successfully deleted user #" + userId;


        }catch(Exception exception){
            return exception.getMessage();

        }
    }

}
