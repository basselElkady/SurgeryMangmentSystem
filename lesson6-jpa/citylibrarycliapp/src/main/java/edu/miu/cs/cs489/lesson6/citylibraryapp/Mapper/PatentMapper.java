package edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Address;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


//@Configuration
@Component
public class PatentMapper {


    private PasswordEncoder passwordEncoder;

    @Autowired
    public PatentMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public PatientResponseDto toPatientDto(Patient patient) {
        return new PatientResponseDto(
                patient.getUsername(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getAddress().getStreet(),
                patient.getAddress().getCity(),
                patient.getAddress().getState(),
                patient.getAddress().getZipCode()
        );

    }

    public Patient toPatient(PatientRequestDTO patientResponseDto) {
        Address address = new Address();
        address.setStreet(patientResponseDto.street());
        address.setCity(patientResponseDto.city());
        address.setState(patientResponseDto.state());
        address.setZipCode(patientResponseDto.zipCode());

        return new Patient(
                patientResponseDto.userName(),
                passwordEncoder.encode(patientResponseDto.password()),
                patientResponseDto.firstName(),
                patientResponseDto.lastName(),
                patientResponseDto.email(),
                patientResponseDto.phoneNumber(),
                Role.PATIENT,
                address
        );
    }

}
