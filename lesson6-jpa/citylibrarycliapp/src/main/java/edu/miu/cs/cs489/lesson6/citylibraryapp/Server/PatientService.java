package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseList;

public interface PatientService {

    PatientResponseList getPatientList();

    void addPatient(PatientRequestDTO patientRequestDTO);

    void deletePatient(Long id);
    PatientResponseDto getPatient(Long id);

    void updatePatient(PatientResponseDto patientResponseDto);





}
