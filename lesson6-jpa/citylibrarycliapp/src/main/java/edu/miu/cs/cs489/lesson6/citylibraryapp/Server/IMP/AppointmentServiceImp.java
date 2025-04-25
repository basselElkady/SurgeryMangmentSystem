package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AppointmentListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AppointmentResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentIsAlreadyDoneException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AppointmentsExceptions.AppointmentNotFoundId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentDidnotPaidTheLastBillException;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.AppointmentMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AppointmentService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.PatientService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.*;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AppointmentRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.DentistRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
public class AppointmentServiceImp implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;
    private PatientService patientService;
    private DentistRepository dentistRepository;
    private PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImp(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, PatientService patientService, DentistRepository dentistRepository
            , PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.patientService = patientService;
        this.dentistRepository = dentistRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void addAppointment(AppointmentRequestDto appointmentRequestDto){
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
    public void cancelAppointment(Long id, Long patientId){
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
    public void payForAppointment(Long id, Long patientId)  {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));

        if (!Objects.equals(appointment.getPatient().getId(), patientId))
            throw new PaitentNotFoundWithId("Patient not found with id " + patientId);
        if(appointment.getState() == AppointmentState.CANCELLED)
            throw new RuntimeException("Appointment is cancelled");
        appointment.getBill().setStatus(BillStatus.PAID);
        appointmentRepository.save(appointment);

    }

    @Override
    public void attendAppointment(Long appointmentId, Long dentistId, Long patientId)  {
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

    @Override // doc can see weekly appointments
    public AppointmentListResponse getAppointmentsForaSpecificDentist(String userName) {
        Dentist dentist = dentistRepository.findByUsername(userName);
        if(dentist == null)
            throw new RuntimeException("Dentist not found with username " + userName);
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Appointment> appointments = appointmentRepository.findByDentistAndDateBetween(dentist, startOfWeek, endOfWeek);
        List<AppointmentResponseDto> appointmentResponseDtos =
                appointments.stream()
                        .map(appointmentMapper::mapToAppointmentResponseDto)
                        .toList();
        return new AppointmentListResponse(appointmentResponseDtos);
    }

    @Override
    public AppointmentListResponse getAppointmentsForaSpecificPatient(
            String userName,
            int page,
            int size) {

        // Validate page and size
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }

        Patient patient = patientRepository.findByUsername(userName);
        if (patient == null) {
            throw new RuntimeException("Patient not found with username " + userName);
        }

        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("date").descending());
        Page<Appointment> appointmentsPage = appointmentRepository.findByPatient(patient, pageable);

        List<AppointmentResponseDto> appointmentResponseDtos = appointmentsPage.getContent()
                .stream()
                .map(appointmentMapper::mapToAppointmentResponseDto)
                .toList();

        // Return additional pagination information
        return new AppointmentListResponse(
                appointmentResponseDtos
        );
    }


}
