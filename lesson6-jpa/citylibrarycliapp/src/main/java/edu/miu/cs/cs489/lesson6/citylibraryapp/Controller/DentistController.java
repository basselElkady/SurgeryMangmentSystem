package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.DentistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dentist")
@AllArgsConstructor
public class DentistController {

    private DentistService dentistService;

    @PostMapping
    public ResponseEntity<Void> addDentist(@RequestBody UserRequestDto userRequestDto) throws DentistWithPhoneNumberAlreadyExist, DentistWithEmailAlreadyExist {
        dentistService.addDentist(userRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDentist(@RequestParam Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getDentist(@RequestParam Long id) {
        UserResponseDto dentist = dentistService.getDentist(id);
        return ResponseEntity.ok(dentist);
    }

    @PutMapping
    public ResponseEntity<Void> updateDentist(@RequestBody UserResponseDto userResponseDto) throws DentistWithPhoneNumberAlreadyExist, DentistWithEmailAlreadyExist, AdminWithEmailAlreadyExist {
        dentistService.updateDentist(userResponseDto);
        return ResponseEntity.ok().build();
    }



}
