package com.leverX.blog.util;


import org.springframework.beans.factory.annotation.Qualifier;

/**
 * This class for  representation response to the client
 *
 * @author Shpakova A.
 */

public class GenericResponse {
    private String message;

    public GenericResponse(String message ) {
        this.message = message;
    }
}
