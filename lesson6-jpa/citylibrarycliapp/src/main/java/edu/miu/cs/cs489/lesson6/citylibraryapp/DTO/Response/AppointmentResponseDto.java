package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

import edu.miu.cs.cs489.lesson6.citylibraryapp.model.AppointmentState;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Bill;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDto(
    Long id,
    LocalDate date,
    LocalTime time,
    String description,
    PatientResponseDto patient,
    UserResponseDto dentist,
    Bill bill,
    AppointmentState state,
    String surgeryName
) {
}
