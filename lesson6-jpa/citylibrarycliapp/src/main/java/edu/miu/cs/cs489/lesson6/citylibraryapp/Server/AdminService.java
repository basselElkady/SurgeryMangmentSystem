package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithPhoneNumberAlreadyExist;

public interface AdminService {

    void addAdmin(UserRequestDto adminRequestDto);

    void deleteAdmin(Long id);

    UserResponseDto getAdmin(Long id);

    void updateAdmin(UserResponseDto adminResponseDto);








}
