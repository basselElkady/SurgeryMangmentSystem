package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaitentNotFoundWithId extends Exception {

    public PaitentNotFoundWithId(String message) {
        super(message);
    }
}
