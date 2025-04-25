package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Appointment;

import java.util.List;

public record AppointmentListResponse(
        List<AppointmentResponseDto> appointmentList
) {
}
