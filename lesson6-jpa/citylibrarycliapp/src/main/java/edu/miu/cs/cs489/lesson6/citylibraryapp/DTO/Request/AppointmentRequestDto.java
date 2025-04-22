package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDto(
    LocalDate date,
    LocalTime time,
    String description,
    Long patientId,
    Long dentistId,
    double amount,
    String surgeryName

) {
}
