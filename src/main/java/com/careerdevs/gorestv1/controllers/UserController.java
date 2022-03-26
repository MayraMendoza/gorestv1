package com.careerdevs.gorestv1.controllers;


import com.careerdevs.gorestv1.models.UserModel;
import com.careerdevs.gorestv1.models.UserModelArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    Environment env;
    
    // URL/ endpoint -> http://localhost:4001/api/user/token
    @GetMapping("/token")
    public String token(){
        return env.getProperty("GOREST_TOKEN");
    }

    // GET http://localhost:4001/api/user/{id}
    //-------------------MANUAL WAY --------------------------
//    @GetMapping("/{id}")
//    public Object getOneUser (
//            @PathVariable("id") String userId,
//            RestTemplate restTemplet) {
//        try {
//
//            String url = "https://gorest.co.in/public/v2/users/" + userId;
//
//            HttpHeaders headers =  new HttpHeaders();
//            headers.setBearerAuth(env.getProperty("GOREST_TOKEN"));
////            String token = env.getProperty("GOREST_TOKEN");
//
//            HttpEntity request = new HttpEntity(headers);
////            headers.setBearerAuth(token);
//            return restTemplet.exchange(
//                    url,
//                    HttpMethod.GET,
//                    request,
//                    Object.class
//            );
//
//
////            return restTemplet.getForObject(url, Object.class);
//
//        }catch(Exception exception){
//                return "404: No user Exists with that ID: " + userId;
//            }
//        }

    // (url/ endpoint) DELETE http://localhost:4001/api/user/firstpage

    @GetMapping("/firstpage")
    public Object getfirstPage(RestTemplate restTemplate){

        try {
            // we are going to start by requesting first page.
            String url  ="https://gorest.co.in/public/v2/users/";
            ResponseEntity<UserModel[]>firstPage = restTemplate.getForEntity(url, UserModel[].class);

            UserModel[] firstPageUser = firstPage.getBody();

            System.out.println("meow");
            for(int i =0; i< firstPageUser.length; i++){
                UserModel tempUser = firstPageUser[i];
                System.out.println(tempUser.generatReport());
            }

            return firstPage;
//            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }





    }

//    @GetMapping("/page/{pageNum}")
//    public Object getPage(RestTemplate restTemplate,
//                          @PathVariable("pageNum")String pageNumber){
//
//        try {
//            // we are going to start by requesting first page.
//
//            String url  ="https://gorest.co.in/public/v2/users?page=" + pageNumber;
//            ResponseEntity<UserModel[]> response= restTemplate.getForEntity(url, UserModel[].class);
//
//            UserModel[] firstPageUser = response.getBody();
//
//           HttpHeaders responseHeaders = response.getHeaders();
//
//         String totalPages = Objects.requireNonNull(responseHeaders.get("X-pagination-Pages").get(0));
//            System.out.println("total pages:"+ totalPages);
//            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);
////            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);
//
//        }catch (Exception e){
//            System.out.println(e.getClass());
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//
//
//
//    }
@GetMapping("/page/{pageNum}")
public Object getPage(RestTemplate restTemplate,
                      @PathVariable("pageNum")String pageNumber){

    try {
        
        // we are going to start by requesting first page.

        String url  ="https://gorest.co.in/public/v2/users?page=" + pageNumber;
        ResponseEntity<UserModel[]> response= restTemplate.getForEntity(url, UserModel[].class);

        UserModel[] firstPageUser = response.getBody();

        HttpHeaders responseHeaders = response.getHeaders();

        String totalPages = Objects.requireNonNull(responseHeaders.get("X-pagination-Pages").get(0));
        System.out.println("total pages:"+ totalPages);
        return new ResponseEntity<>(firstPageUser,HttpStatus.OK);
//            return new ResponseEntity<>(firstPageUser,HttpStatus.OK);

    }catch (Exception e){
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }





}






    // GET http://localhost:4001/api/user/{id}
    @GetMapping("/{id}")
    public Object getOneUser (
            @PathVariable("id") String userId,
            RestTemplate restTemplet) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String apiToken= env.getProperty("GOREST_TOKEN");
            // quary parameter part
            url += "?access-token=" + apiToken;

            return restTemplet.getForObject(url, Object.class);




//            return restTemplet.getForObject(url, Object.class);

        }catch(Exception exception){
            return "404: No user Exists with that ID: " + userId;
        }
    }
//----------------------Manual Way-------------------------------------
        // deleting a user
    // (url/ endpoint) DELETE http://localhost:4001/api/user/{id}

//    @DeleteMapping("/{id}")
//    public Object deleteOneUser (
//            @PathVariable("id") String userId,
//            RestTemplate restTemplet) {
//        try {
//
//            String url = "https://gorest.co.in/public/v2/users/" + userId;
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBearerAuth(env.getProperty("GOREST_TOKEN"));
//            HttpEntity request = new HttpEntity(headers);
//
////            ResponseEntity<Object> response =
//            restTemplet.exchange(
//                    url,
//                    HttpMethod.DELETE,
//                    request,
//                    Object.class);
//
////            headers.set("Authorization", "Bearer " + token);
//
////
////            restTemplet.delete(url);
//            return "Successfully deleted user #" + userId;
//
//        } catch (Exception exception) {
//            return exception.getMessage();
//
//        }
//    }


// deleting a user
// (url/ endpoint) DELETE http://localhost:4001/api/user/{id}
    @DeleteMapping("/{id}")
    public Object deleteOneUser (
            @PathVariable("id") String userId,
            RestTemplate restTemplet) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;

            restTemplet.delete(url);
            return restTemplet.getForObject(url, Object.class);

        } catch (HttpClientErrorException.NotFound exception){
            return "User could not be deleted, user #" +userId+ " does not exist";

        } catch (HttpClientErrorException.Unauthorized exception){
            return "you are not authorized to delete user # "+ userId;
        } catch (Exception exception) {
            System.out.println(exception.getClass());
            return exception.getMessage();

        }
    }
// three ways path variables, request param & request body
//    @PostMapping ("/")
//    public Object postUser(
//            @RequestParam ("name") String name,
//            @RequestParam ("email") String email,
//            @RequestParam ("gender") String gender,
//            @RequestParam ("status") String status,
//            RestTemplate restTemplate
//
//    ) {
//        try {
//            String url = "http://gorest.co.in/public/v2/users/";
//            String token = env.getProperty("GOREST_TOKEN");
//            url += "?access-token=" + token;
//            UserModel newUser = new UserModel(name, email, gender, status);
////            HttpHeaders headers = new HttpHeaders();
////
////            headers.setBearerAuth(token);
//            HttpEntity<UserModel> request = new HttpEntity<>(newUser);
//
//            return  restTemplate.postForEntity(url, request, UserModel.class);
//
//        } catch (Exception exception) {
//            System.out.println(exception.getClass());
//            return exception.getMessage();
//
//        }
//    }

    @PostMapping ("/qp")
    public Object postUserQueryParam(
            @RequestParam ("name") String name,
            @RequestParam ("email") String email,
            @RequestParam ("gender") String gender,
            @RequestParam ("status") String status,
            RestTemplate restTemplate

            ){
        try{
            String url = "http://gorest.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token" +token;
            UserModel newUser = new UserModel(name, email, gender, status);
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(token);
            HttpEntity <UserModel>request = new HttpEntity<>(newUser);

            return restTemplate.postForEntity(url, request, UserModel.class);


        } catch (Exception exception){
            System.out.println(exception.getClass());
            return exception.getMessage();

        }
    }
//
    @PostMapping("/")
    public ResponseEntity postUser (
            RestTemplate restTemplate,
            @RequestBody UserModel newUser
    ){
        try{
            String url = "http://gorest.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token" +token;

            HttpEntity<UserModel> request = new HttpEntity<UserModel>(newUser);


            return restTemplate.postForEntity(url, request, UserModel.class);
//            return new ResponseEntity<>("Success!", HttpStatus.OK);

        } catch (Exception e){
            System.out.println(e.getClass() + "\n"+ e.getMessage());
            //cant return a string b/c it is expecting a response entity.
            //wii contain a status code along with e.getmessage a
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
