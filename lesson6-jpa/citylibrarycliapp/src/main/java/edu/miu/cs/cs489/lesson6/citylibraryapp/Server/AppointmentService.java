package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;

public interface AppointmentService {

    public void addAppointment(AppointmentRequestDto appointmentRequestDto) throws PaitentDidnotPaidTheLastBillException;

    public void cancelAppointment(Long appointmentId,  Long patientId) throws AppointmentNotFoundId, AppointmentIsAlreadyDoneException, PaitentNotFoundWithId;

    public void payForAppointment(Long appointmentId, Long patientId) throws PaitentNotFoundWithId;

    public void attendAppointment(Long appointmentId, Long dentistId, Long patientId) throws PaitentNotFoundWithId, DentistNotFoundWithId;



}
