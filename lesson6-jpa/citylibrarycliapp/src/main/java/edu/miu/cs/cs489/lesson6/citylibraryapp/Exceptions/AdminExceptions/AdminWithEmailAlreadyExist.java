package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AdminWithEmailAlreadyExist extends Exception{

    public AdminWithEmailAlreadyExist(String message) {
        super(message);
    }

}
