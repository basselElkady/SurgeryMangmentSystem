package edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper;


import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Admin;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Dentist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UserMapper {

    public UserResponseDto toDentistDto(Dentist dentist) {
        return new UserResponseDto(
                dentist.getId(),
                dentist.getFirstName(),
                dentist.getLastName(),
                dentist.getEmail(),
                dentist.getPhoneNumber()
        );

    }

    public UserResponseDto toAdminDto(Admin dentist) {
        return new UserResponseDto(
                dentist.getId(),
                dentist.getFirstName(),
                dentist.getLastName(),
                dentist.getEmail(),
                dentist.getPhoneNumber()
        );

    }



    public Dentist toDentist(UserRequestDto dentistResponseDto) {

        return new Dentist(
                dentistResponseDto.firstName(),
                dentistResponseDto.lastName(),
                dentistResponseDto.email(),
                dentistResponseDto.phoneNumber(),
                Role.DENTIST
        );
    }


    public Admin toUser(UserRequestDto dentistResponseDto) {

        return new Admin(
                dentistResponseDto.firstName(),
                dentistResponseDto.lastName(),
                dentistResponseDto.email(),
                dentistResponseDto.phoneNumber(),
                Role.ADMIN
        );
    }




}
