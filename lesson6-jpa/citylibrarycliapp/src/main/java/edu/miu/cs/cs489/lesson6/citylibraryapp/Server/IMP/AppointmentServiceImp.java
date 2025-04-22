package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.AppointmentMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AppointmentService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.PatientService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Appointment;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.AppointmentState;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.BillStatus;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@NoArgsConstructor
public class AppointmentServiceImp implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;
    private PatientService patientService;

    @Autowired
    public AppointmentServiceImp(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.patientService = patientService;
    }

    @Override
    public void addAppointment(AppointmentRequestDto appointmentRequestDto) throws PaitentDidnotPaidTheLastBillException {
        Appointment appointment = appointmentMapper.mapToAppointment(appointmentRequestDto);
        Patient patient = appointment.getPatient();


        if(patient.getLastAppointment() != null) {
            if (!patientService.validToAddAppointment(patient.getLastAppointment()))
                throw new PaitentDidnotPaidTheLastBillException("Patient has not paid for the appointment for date " + appointment.getDate());
        }
        patient.getAppointments().add(appointment);
        patient.setLastAppointment(appointment);

        appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(Long id, Long patientId) throws AppointmentNotFoundId, AppointmentIsAlreadyDoneException, PaitentNotFoundWithId {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundId("Appointment not found with id " + id));
        if (appointment.getState() == AppointmentState.DONE)
            throw new AppointmentIsAlreadyDoneException("Appointment is already done");
        if (!Objects.equals(appointment.getPatient().getId(), patientId))
            throw new PaitentNotFoundWithId("Patient not found with id " + patientId);
        appointment.setState(AppointmentState.CANCELLED);
        appointment.getBill().setStatus(BillStatus.CANCELLED);
        appointmentRepository.save(appointment);

    }

    @Override
    public void payForAppointment(Long id, Long patientId) throws PaitentNotFoundWithId {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));

        if (!Objects.equals(appointment.getPatient().getId(), patientId))
            throw new PaitentNotFoundWithId("Patient not found with id " + patientId);
        appointment.getBill().setStatus(BillStatus.PAID);
        appointmentRepository.save(appointment);

    }

    @Override
    public void attendAppointment(Long appointmentId, Long dentistId, Long patientId) throws PaitentNotFoundWithId, DentistNotFoundWithId {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found with id " + appointmentId));

        if (!Objects.equals(appointment.getPatient().getId(), patientId))
            throw new PaitentNotFoundWithId("Patient not found with id " + patientId);
        if (!Objects.equals(appointment.getDentist().getId(), dentistId))
            throw new DentistNotFoundWithId("Dentist not found with id " + dentistId);
        if ((java.time.LocalDate.now().isAfter(appointment.getDate()) || appointment.getDate().isEqual(java.time.LocalDate.now())) &&
                (appointment.getTime().isAfter(java.time.LocalTime.now())))
            appointment.setState(AppointmentState.DONE);
        appointmentRepository.save(appointment);

    }





}
