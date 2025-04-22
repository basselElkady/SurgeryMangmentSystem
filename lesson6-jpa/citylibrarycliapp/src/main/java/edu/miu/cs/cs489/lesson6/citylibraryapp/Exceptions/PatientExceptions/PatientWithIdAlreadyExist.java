package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PatientWithIdAlreadyExist extends RuntimeException{

    public PatientWithIdAlreadyExist(String message) {
        super(message);
    }

}
