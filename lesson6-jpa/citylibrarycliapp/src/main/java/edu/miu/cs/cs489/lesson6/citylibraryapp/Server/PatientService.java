package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseList;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Appointment;

import java.util.Deque;

public interface PatientService {

    PatientResponseList getPatientList();

    void addPatient(PatientRequestDTO patientRequestDTO);

    void deletePatient(String userName) ;
    PatientResponseDto getPatient(Long id) ;

    void updatePatient(PatientResponseDto patientResponseDto) ;

    boolean validToAddAppointment(Appointment appointment);




}
