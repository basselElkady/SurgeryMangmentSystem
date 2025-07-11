package edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.AppointmentRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.AppointmentResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.*;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AppointmentRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.DentistRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.SurgeryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@NoArgsConstructor
public class AppointmentMapper {

    private PatientRepository patientRepository;
    private DentistRepository dentistRepository;
    private PatentMapper patientMapper;
    private UserMapper dentistMapper;
    private SurgeryRepository surgeryRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentMapper(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DentistRepository dentistRepository, PatentMapper patientMapper, UserMapper dentistMapper, SurgeryRepository surgeryRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.patientMapper = patientMapper;
        this.dentistMapper = dentistMapper;
        this.surgeryRepository = surgeryRepository;

    }

    public Appointment mapToAppointment(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentRequestDto.date());
        appointment.setTime(appointmentRequestDto.time());
        appointment.setDescription(appointmentRequestDto.description());
        appointment.setPatient(patientRepository.findById(appointmentRequestDto.patientId()).orElseThrow(()-> new RuntimeException("Patient not found with id " + appointmentRequestDto.patientId())));

        //appointment.setDentist(dentistRepository.findById(appointmentRequestDto.dentistId()).orElseThrow(()-> new RuntimeException("Dentist not found with id " + appointmentRequestDto.dentistId())));

        Dentist dentist = dentistRepository.findById(appointmentRequestDto.dentistId()).orElseThrow(()-> new RuntimeException("Dentist not found with id " + appointmentRequestDto.dentistId()));
        //LocalDate startOfWeek = dateInWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        appointment.setDentist(dentist);

        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Appointment> appointments = appointmentRepository.findByDentistAndDateBetween(dentist, startOfWeek, endOfWeek);
        //System.out.println("here are the appointments "+appointments.size()+" start "+startOfWeek+" end "+endOfWeek);
        if(appointments.size() >= 5) throw new RuntimeException("Dentist is full");

        for(Appointment a : appointments) {
            if(a.getDate().isEqual(appointment.getDate()) && a.getTime().equals(appointment.getTime())) {
                throw new RuntimeException("Dentist is full");
            }
        }

        appointment.setState(AppointmentState.SCHEDULED);
        Bill bill = new Bill();
        bill.setAmount(appointmentRequestDto.amount());
        bill.setStatus(BillStatus.PENDING);
        appointment.setBill(bill);
        Surgery surgery = surgeryRepository.findByName(appointmentRequestDto.surgeryName());
        if(surgery == null) throw new RuntimeException("Surgery not found with name " + appointmentRequestDto.surgeryName());
        appointment.setSurgery(surgery);
        return appointment;

    }

    public AppointmentResponseDto mapToAppointmentResponseDto(Appointment appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getDescription(),
                patientMapper.toPatientDto(appointment.getPatient()),
                dentistMapper.toDentistDto(appointment.getDentist()),
                appointment.getBill(),
                appointment.getState(),
                appointment.getSurgery().getName(),
                appointment.getSurgery().getAddress().getStreet(),
                appointment.getSurgery().getAddress().getCity(),
                appointment.getSurgery().getAddress().getState(),
                appointment.getSurgery().getAddress().getZipCode()

        );
    }





}
