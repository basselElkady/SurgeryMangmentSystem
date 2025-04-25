package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SurgeryNotFoundWithId extends RuntimeException{
    public SurgeryNotFoundWithId(String message) {
        super(message);
    }
}
