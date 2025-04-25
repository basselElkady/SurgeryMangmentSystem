package edu.miu.cs.cs489.lesson6.citylibraryapp.Service;


import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.PatientExceptions.PatientWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.PatentMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP.PatentServiceImp;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Patient;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.PatientRepository;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @InjectMocks
    private PatentServiceImp patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatentMapper patentMapper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPatient_Success() {
        PatientRequestDTO dto = new PatientRequestDTO("jdoe", "password", "John", "Doe", "jdoe@example.com", "1234567890", "123 Main", "City", "State", "12345");

        when(userRepository.existsByUsername(dto.userName())).thenReturn(false);
        when(patientRepository.existsByEmail(dto.email())).thenReturn(false);
        when(patientRepository.existsByPhoneNumber(dto.phoneNumber())).thenReturn(false);
        Patient patient = new Patient();
        when(patentMapper.toPatient(dto)).thenReturn(patient);

        patientService.addPatient(dto);

        verify(patientRepository).save(patient);
    }

    @Test
    void testAddPatient_EmailExists_ThrowsException() {
        PatientRequestDTO dto = new PatientRequestDTO("jdoe", "password","John", "Doe", "jdoe@example.com", "1234567890", "123 Main", "City", "State", "12345");

        when(userRepository.existsByUsername(dto.userName())).thenReturn(false);
        when(patientRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(PatientWithEmailAlreadyExist.class, () -> patientService.addPatient(dto));
    }

    @Test
    void testGetPatient_Success() {
        Long id = 1L;
        Patient patient = new Patient();
        PatientResponseDto responseDto = new PatientResponseDto("jdoe", "John", "Doe", "jdoe@example.com", "1234567890", "123 Main", "City", "State", "12345");

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patentMapper.toPatientDto(patient)).thenReturn(responseDto);

        PatientResponseDto result = patientService.getPatient(id);

        assertEquals("jdoe", result.username());
    }

    @Test
    void testDeletePatient_Success() {
        Patient patient = new Patient();
        patient.setUsername("jdoe");

        when(patientRepository.findByUsername("jdoe")).thenReturn(patient);

        patientService.deletePatient("jdoe");

        verify(patientRepository).delete(patient);
    }

}
