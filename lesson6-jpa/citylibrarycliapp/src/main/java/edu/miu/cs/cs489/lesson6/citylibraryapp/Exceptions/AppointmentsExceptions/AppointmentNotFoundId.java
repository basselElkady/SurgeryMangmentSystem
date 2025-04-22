package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFoundId extends Exception{

    public AppointmentNotFoundId(String message) {
        super(message);
    }
}
