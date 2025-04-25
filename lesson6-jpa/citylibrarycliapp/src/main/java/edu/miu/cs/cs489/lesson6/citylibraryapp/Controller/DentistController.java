package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.DentistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dentist")
@AllArgsConstructor
public class DentistController {

    private DentistService dentistService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> addDentist(@RequestBody UserRequestDto userRequestDto) {
        dentistService.addDentist(userRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDentist(@RequestParam String userName) {
        dentistService.deleteDentist(userName);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DENTIST')")
    public ResponseEntity<UserResponseDto> getDentist(@RequestParam Long id) {
        UserResponseDto dentist = dentistService.getDentist(id);
        return ResponseEntity.ok(dentist);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('DENTIST') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> updateDentist(@RequestBody UserResponseDto userResponseDto)  {
        dentistService.updateDentist(userResponseDto);
        return ResponseEntity.ok().build();
    }



}
