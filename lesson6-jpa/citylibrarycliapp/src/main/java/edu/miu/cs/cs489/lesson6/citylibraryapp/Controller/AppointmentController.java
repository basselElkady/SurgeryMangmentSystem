package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AppointmentListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")

public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PATIENT')")
    public ResponseEntity<Void> addAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        appointmentService.addAppointment(appointmentRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
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

    @GetMapping("/patients")
    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    public ResponseEntity<AppointmentListResponse> getAppointmentsForaSpecificPatient(@RequestParam String userName
                                        , @RequestParam int page, @RequestParam int size) {

        AppointmentListResponse appointmentListResponse = appointmentService.getAppointmentsForaSpecificPatient(userName, page, size);
        return ResponseEntity.ok(appointmentListResponse);

    }

    @GetMapping("/dentists")
    @PreAuthorize("hasAuthority('DENTIST') or hasAuthority('ADMIN')")
    public ResponseEntity<AppointmentListResponse> getAppointmentsForaSpecificDentist(@RequestParam String userName) {

        AppointmentListResponse appointmentListResponse = appointmentService.getAppointmentsForaSpecificDentist(userName);
        return ResponseEntity.ok(appointmentListResponse);

    }



}
