package com.vet.controller;

public class GenericResponse {
    public String status;
    public String description;

    public GenericResponse(String status, String description) {
        this.status = status;
        this.description = description;
    }
}