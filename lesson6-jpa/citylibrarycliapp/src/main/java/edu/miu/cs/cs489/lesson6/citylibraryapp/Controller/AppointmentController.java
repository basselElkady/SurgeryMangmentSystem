package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Void> addAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) throws PaitentDidnotPaidTheLastBillException {
        appointmentService.addAppointment(appointmentRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> cancelAppointment(@RequestParam String operation,@RequestParam Long appointmentId
            , @RequestParam Long patientId, @RequestParam(required = false) Long dentistId) throws PaitentNotFoundWithId, AppointmentNotFoundId, AppointmentIsAlreadyDoneException, DentistNotFoundWithId {
        switch (operation) {
            case "cancel" -> appointmentService.cancelAppointment(appointmentId, patientId);
            case "pay" -> appointmentService.payForAppointment(appointmentId, patientId);
            case "attend" -> {
                if (dentistId == null) throw new RuntimeException("Dentist id is required");
                appointmentService.attendAppointment(appointmentId, dentistId, patientId);
            }
        }
        return ResponseEntity.ok().build();
    }



}
