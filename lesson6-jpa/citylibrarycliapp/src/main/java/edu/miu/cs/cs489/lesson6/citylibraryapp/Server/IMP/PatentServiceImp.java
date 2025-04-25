package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseList;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PaitentNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PatientWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PatientWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.PatentMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.PatientService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Appointment;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Bill;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.BillStatus;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Deque;
import java.util.List;

@Service
@NoArgsConstructor
public class PatentServiceImp implements PatientService {

    private PatientRepository patientRepository;
    private PatentMapper patentMapper;
    private UserRepository userRepository;

    @Autowired
    public PatentServiceImp(PatientRepository patientRepository, PatentMapper patentMapper, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.patentMapper = patentMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public PatientResponseList getPatientList() {
        List<Patient> patientServices = patientRepository.findAll();
        if(patientServices.isEmpty()) {
            return null;
        }
        return new PatientResponseList(patientServices);
    }

    @Transactional
    @Override
    public void addPatient(PatientRequestDTO patientRequestDto) {
        if(userRepository.existsByUsername(patientRequestDto.userName()))
            throw new PatientWithEmailAlreadyExist("Patient with username " + patientRequestDto.userName() + " already exists");

        if(patientRepository.existsByEmail(patientRequestDto.email()))
            throw new PatientWithEmailAlreadyExist("Patient with email " + patientRequestDto.email() + " already exists"); // exception should be thrown already exists

        if(patientRepository.existsByPhoneNumber(patientRequestDto.phoneNumber()))
            throw new PatientWithPhoneNumberAlreadyExist("Patient with phone number " + patientRequestDto.phoneNumber() + " already exists"); // exception should be thrown already exists

        Patient patient = patentMapper.toPatient(patientRequestDto);
        patientRepository.save(patient);
    }

    @Transactional
    @Override
    public void deletePatient(String userName) {
        Patient patient = patientRepository.findByUsername(userName);
        if(patient == null) {
            throw new RuntimeException("Patient not found with username " + userName);
        }
        patientRepository.delete(patient);
    }

    @Transactional
    @Override
    public PatientResponseDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PaitentNotFoundWithId("Patient not found with id " + id));
        return patentMapper.toPatientDto(patient);
    }



    @Transactional
    @Override
    public void updatePatient(PatientResponseDto patientResponseDto) {
        Patient patient = patientRepository.findByUsername(patientResponseDto.username());
        if(patient == null)
            throw new RuntimeException("Patient not found with username " + patientResponseDto.username());

        if(patientResponseDto.firstName() != null)
            patient.setFirstName(patientResponseDto.firstName());
        if(patientResponseDto.lastName() != null)
            patient.setLastName(patientResponseDto.lastName());
        if(patientResponseDto.email() != null) {
            if(patientRepository.existsByEmail(patientResponseDto.email())) {
                throw new PatientWithEmailAlreadyExist("Patient with email " + patientResponseDto.email() + " already exists");
            }
            patient.setEmail(patientResponseDto.email());
        }
        if(patientResponseDto.phoneNumber() != null)
            patient.setPhoneNumber(patientResponseDto.phoneNumber());
        if(patientResponseDto.street() != null)
            patient.getAddress().setStreet(patientResponseDto.street());
        if(patientResponseDto.city() != null)
            patient.getAddress().setCity(patientResponseDto.city());
        if(patientResponseDto.state() != null)
            patient.getAddress().setState(patientResponseDto.state());
        if(patientResponseDto.zipCode() != null)
            patient.getAddress().setZipCode(patientResponseDto.zipCode());
        //System.out.println("Patient updated: " + patient.getFirstName());
        patientRepository.save(patient);

    }

    @Override
    public boolean validToAddAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("No appointment available to validate");
        Bill bill = appointment.getBill();
        if(bill == null) throw new RuntimeException("Bill not found");
        return bill.getStatus() == BillStatus.PAID || bill.getStatus() == BillStatus.CANCELLED;
    }
}
