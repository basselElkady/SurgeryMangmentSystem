package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PatientWithEmailAlreadyExist extends RuntimeException {

    public PatientWithEmailAlreadyExist(String message) {
        super(message);
    }
}
