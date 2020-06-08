package com.leverX.blog.util;

public class GenericResponse { // объект GenericResponse- для представления нашего ответа клиенту
    private String message;
    private String error;

    public GenericResponse(String message) {
        super();
        this.message = message;
    }

    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }
}
