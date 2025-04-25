package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithPhoneNumberAlreadyExist;

public interface DentistService {

    public UserResponseDto getDentist(Long id);

    public void deleteDentist(String userName);

    public void addDentist(UserRequestDto userRequestDto) ;

    public void updateDentist(UserResponseDto userResponseDto) throws AdminWithEmailAlreadyExist, DentistWithEmailAlreadyExist, DentistWithPhoneNumberAlreadyExist;


}
