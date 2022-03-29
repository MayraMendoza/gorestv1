package com.careerdevs.gorestv1.models;
//        "id": 1942,
//        "post_id": 1933,
//        "name": "Gov. Devi Jha",
//        "email": "devi_gov_jha@daugherty.biz",
//        "body": "Veniam laborum alias."

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentModel {
    private int id;
    // to change field name
    //will recognize Post_id in json data and store it in post id
    @JsonProperty("post_id")
    private int postId;
    private String name;
    private String email;
    private String body;

// dont delete, keep your default constructor just in case.
    public CommentModel() {
    }

    public int getId() {
        return id;
    }

    public int getPosId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
