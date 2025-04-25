package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SurgeryWithNameAlreadyExist extends RuntimeException{
    public SurgeryWithNameAlreadyExist(String message) {
        super(message);
    }
}
