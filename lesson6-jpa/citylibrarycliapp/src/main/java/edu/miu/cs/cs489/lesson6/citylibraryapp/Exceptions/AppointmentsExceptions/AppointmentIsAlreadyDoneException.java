package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AppointmentIsAlreadyDoneException extends Exception{
    public AppointmentIsAlreadyDoneException(String message) {
        super(message);
    }
}
