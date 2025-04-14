package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseList;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.PatentMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.PatientService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PatentServiceImp implements PatientService {

    private PatientRepository patientRepository;
    private PatentMapper patentMapper;

    @Override
    public PatientResponseList getPatientList() {
        List<Patient> patientServices = patientRepository.findAll();
        if(patientServices.isEmpty()) {
            return null;
        }
        return new PatientResponseList(patientServices);
    }

    @Override
    public void addPatient(PatientRequestDTO patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.email())) {
            return; // exception should be thrown already exists
        }
        Patient patient = patentMapper.toPatient(patientRequestDto);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) {
            return; // exception should be thrown no such patient with id
        }
        patientRepository.delete(patient);
    }

    @Override
    public PatientResponseDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
        return patentMapper.toPatientDto(patient);
    }


    @Override
    public void updatePatient(PatientResponseDto patientResponseDto) {
        Patient patient = patientRepository.findById(patientResponseDto.id()).orElseThrow(() -> new RuntimeException("Patient not found with id " + patientResponseDto.id()));
        patient.setFirstName(patientResponseDto.firstName());
        patient.setLastName(patientResponseDto.lastName());
        patient.setEmail(patientResponseDto.email());
        patient.setPhoneNumber(patientResponseDto.phoneNumber());
        patientRepository.save(patient);
    }
}
