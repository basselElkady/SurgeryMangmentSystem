package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DentistWithIdAlreadyExist extends RuntimeException {

    public DentistWithIdAlreadyExist(String message) {
        super(message);
    }
}
