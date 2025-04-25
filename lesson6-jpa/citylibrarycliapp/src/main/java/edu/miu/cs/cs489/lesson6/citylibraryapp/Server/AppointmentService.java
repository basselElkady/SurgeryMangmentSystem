package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AppointmentListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;

public interface AppointmentService {

    public void addAppointment(AppointmentRequestDto appointmentRequestDto);

    public void cancelAppointment(Long appointmentId,  Long patientId);

    public void payForAppointment(Long appointmentId, Long patientId) ;

    public void attendAppointment(Long appointmentId, Long dentistId, Long patientId);

    public AppointmentListResponse getAppointmentsForaSpecificDentist(String userName);

    public AppointmentListResponse getAppointmentsForaSpecificPatient( String userName,
                                                                       int page,
                                                                       int size);



}
