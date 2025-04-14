package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.PatientRequestDTO;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.PatientResponseList;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class PatientController {

        private final PatientService patientService;


        // Get all patients
        @GetMapping(produces = "application/json")
        public ResponseEntity<PatientResponseList> getAllPatients() {
            PatientResponseList response = patientService.getPatientList();
            if (response == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        }

        // Get single patient by ID
        @GetMapping(value = "/{id}", produces = "application/json")
        public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
            try {
                PatientResponseDto patient = patientService.getPatient(id);
                return ResponseEntity.ok(patient);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        // Add new patient
        @PostMapping(consumes = "application/json", produces = "application/json")
        public ResponseEntity<String> addPatient(@RequestBody PatientRequestDTO patientRequestDto) {
            patientService.addPatient(patientRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Patient created successfully");
        }

        // Update patient
        @PutMapping(consumes = "application/json", produces = "application/json")
        public ResponseEntity<String> updatePatient(@RequestBody PatientResponseDto patientResponseDto) {
            try {
                patientService.updatePatient(patientResponseDto);
                return ResponseEntity.ok("Patient updated successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }
        }

        // Delete patient
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deletePatient(@PathVariable Long id) {
            try {
                patientService.deletePatient(id);
                return ResponseEntity.ok("Patient deleted successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Patient not found with id: " + id);
            }
        }


}
