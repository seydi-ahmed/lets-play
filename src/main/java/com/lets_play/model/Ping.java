package com.lets_play.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ping")
public class Ping {
    @Id
    private String id;
    private String message;

    // constructeur par défaut
    public Ping() {}

    // constructeur paramétré
    public Ping(String message) {
        this.message = message;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
