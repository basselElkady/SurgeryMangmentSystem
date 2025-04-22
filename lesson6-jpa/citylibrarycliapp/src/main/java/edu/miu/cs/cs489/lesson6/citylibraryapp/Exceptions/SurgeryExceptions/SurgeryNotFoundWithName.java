package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class SurgeryNotFoundWithName extends Exception{
    public SurgeryNotFoundWithName(String message) {
        super(message);
    }
}
