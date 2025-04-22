package edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PaitentDidnotPaidTheLastBillException extends Exception{

    public PaitentDidnotPaidTheLastBillException(String message) {
        super(message);
    }

}
