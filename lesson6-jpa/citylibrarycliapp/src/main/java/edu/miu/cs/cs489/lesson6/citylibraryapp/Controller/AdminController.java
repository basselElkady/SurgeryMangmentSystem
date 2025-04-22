package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AdminService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.DentistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {


    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Void> addAdmin(@RequestBody UserRequestDto userRequestDto) throws AdminWithPhoneNumberAlreadyExist, AdminWithEmailAlreadyExist {
        adminService.addAdmin(userRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAdmin(@RequestParam Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getAdmin(@RequestParam Long id) {
        UserResponseDto dentist = adminService.getAdmin(id);
        return ResponseEntity.ok(dentist);
    }

    @PutMapping
    public ResponseEntity<Void> updateAdmin(@RequestBody UserResponseDto userResponseDto) {
        adminService.updateAdmin(userResponseDto);
        return ResponseEntity.ok().build();
    }

}
