package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdminNotFoundWithId extends Exception{

    public AdminNotFoundWithId(String message) {
        super(message);
    }
}
