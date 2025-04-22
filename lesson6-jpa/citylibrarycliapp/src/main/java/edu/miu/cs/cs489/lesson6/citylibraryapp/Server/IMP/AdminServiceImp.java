package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.UserMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.AdminService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Admin;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Dentist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImp implements AdminService {

    private AdminRepository adminRepository;
    private UserMapper userMapper;
    @Override
    public void addAdmin(UserRequestDto adminRequestDto) throws AdminWithEmailAlreadyExist, AdminWithPhoneNumberAlreadyExist {

        if(adminRepository.existsByPhoneNumber(adminRequestDto.email()))
            throw new AdminWithEmailAlreadyExist("Admin with email " + adminRequestDto.email() + " already exists");
        if(adminRepository.existsByPhoneNumber(adminRequestDto.phoneNumber()))
            throw new AdminWithPhoneNumberAlreadyExist("Admin with phone number " + adminRequestDto.phoneNumber() + " already exists");
        adminRepository.save(userMapper.toUser(adminRequestDto));

    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found with id " + id));
        adminRepository.delete(admin);
    }

    @Override
    public UserResponseDto getAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found with id " + id));
        return userMapper.toAdminDto(admin);
    }

    @Override
    public void updateAdmin(UserResponseDto adminResponseDto) {

        Admin dentist = adminRepository.findById(adminResponseDto.id()).orElseThrow(() -> new RuntimeException("Dentist not found with id " + adminResponseDto.id()));
        if (adminResponseDto.firstName() != null)
            dentist.setFirstName(adminResponseDto.firstName());
        if (adminResponseDto.lastName() != null)
            dentist.setLastName(adminResponseDto.lastName());
        if (adminResponseDto.email() != null && !adminRepository.existsByEmail(adminResponseDto.email()))
            dentist.setEmail(adminResponseDto.email());
        else
            throw new RuntimeException("Dentist with email " + adminResponseDto.email() + " already exists");
        if (adminResponseDto.phoneNumber() != null) {
            if (adminRepository.existsByPhoneNumber(adminResponseDto.phoneNumber()))
                throw new RuntimeException("Dentist with phone number " + adminResponseDto.phoneNumber() + " already exists");
            dentist.setPhoneNumber(adminResponseDto.phoneNumber());

        }
        adminRepository.save(dentist);

    }
}
