package com.careerdevs.gorestv1.controllers;

import com.careerdevs.gorestv1.models.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    // when we post/delete or updata a variable we will need environment variable and @autowire

    @Autowired
    Environment env;

    @GetMapping ("/firstpage")
    public CommentModel[] getFirstPage(RestTemplate restTemplate){
        String url = "https://gorest.co.in/public/v2/comments/";
        //
        return restTemplate.getForObject(url, CommentModel[].class);

    }
    // getting a single comment by id
    //no array just simple instance
    // path variable controls id
    @GetMapping ("/{id}")
    public ResponseEntity getOneComment (RestTemplate restTemplate, @PathVariable("id") int CommentId){
        try{
        String url = "https://gorest.co.in/public/v2/comments/" + CommentId;
        return new ResponseEntity(restTemplate.getForObject(url, CommentModel.class), HttpStatus.OK);

    }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneComment (RestTemplate restTemplate, @PathVariable("id") int CommentId) {
        try{
            String url = "https://gorest.co.in/public/v2/comments/" + CommentId;
            String token =  env.getProperty("GOREST_TOKEN");
            url += "?access-token=" +token;

            CommentModel deletedComment = restTemplate.getForObject(url, CommentModel.class);
            restTemplate.delete(url);

            return new ResponseEntity<>(deletedComment, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
// you can omit / -
    @PostMapping
    public ResponseEntity<Object> postComment (
            RestTemplate restTemplate,
            @RequestBody CommentModel newComment
            ) {
        try {
            String url = "https://gorest.co.in/public/v2/comments/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;
            HttpEntity<CommentModel> request = new HttpEntity<>(newComment);

            CommentModel createdComment = restTemplate.postForObject(url, request,CommentModel.class);


            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    public ResponseEntity<Object> putComment(
            RestTemplate restTemplate,
            @RequestBody CommentModel updatedComment
    ){ try{
        String url = "https://gorest.co.in/public/v2/comments/" + updatedComment;
        String token = env.getProperty("GOREST_TOKEN");
        url += "?access-token" + token;

        HttpEntity<CommentModel> request = new HttpEntity<>(updatedComment);

        // we can do exchange or a put method.
        ResponseEntity<CommentModel> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                CommentModel.class);


        return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
    } catch (Exception e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

}
