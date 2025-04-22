package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AdminWithIdAlreadyExist extends Exception {

    public AdminWithIdAlreadyExist(String message) {
        super(message);
    }

}
