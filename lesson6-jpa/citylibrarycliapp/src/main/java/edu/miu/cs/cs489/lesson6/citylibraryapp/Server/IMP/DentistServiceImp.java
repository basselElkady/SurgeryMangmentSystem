package edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.DentistExceptions.DentistWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.UserMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.DentistService;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Dentist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.DentistRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class DentistServiceImp implements DentistService {

    private DentistRepository dentistRepository;
    private UserMapper userMapper;

    @Autowired
    public DentistServiceImp(DentistRepository dentistRepository, UserMapper userMapper) {
        this.dentistRepository = dentistRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public UserResponseDto getDentist(Long id) {
        Dentist dentist = dentistRepository.findById(id).orElseThrow(() -> new RuntimeException("Dentist not found with id " + id));
        UserResponseDto userResponseDto = userMapper.toDentistDto(dentist);
        return userResponseDto;
    }

    @Transactional
    @Override
    public void deleteDentist(Long id) {
        Dentist dentist = dentistRepository.findById(id).orElseThrow(() -> new RuntimeException("Dentist not found with id " + id));
        dentistRepository.delete(dentist);
    }

    @Transactional
    @Override
    public void addDentist(UserRequestDto userRequestDto) throws DentistWithEmailAlreadyExist, DentistWithPhoneNumberAlreadyExist {
        if (dentistRepository.existsByPhoneNumber(userRequestDto.phoneNumber()))
            throw new DentistWithEmailAlreadyExist("Dentist with email " + userRequestDto.email() + " already exists");
        if(dentistRepository.existsByEmail(userRequestDto.email()))
            throw new DentistWithPhoneNumberAlreadyExist("Dentist with phone number " + userRequestDto.phoneNumber() + " already exists");
        Dentist dentist = userMapper.toDentist(userRequestDto);
        dentistRepository.save(dentist);
    }

    @Transactional
    @Override
    public void updateDentist(UserResponseDto userResponseDto) throws DentistWithEmailAlreadyExist, DentistWithPhoneNumberAlreadyExist {
        Dentist dentist = dentistRepository.findById(userResponseDto.id()).orElseThrow(() -> new RuntimeException("Dentist not found with id " + userResponseDto.id()));
        if (userResponseDto.firstName() != null)
            dentist.setFirstName(userResponseDto.firstName());
        if (userResponseDto.lastName() != null)
            dentist.setLastName(userResponseDto.lastName());
        if (userResponseDto.email() != null) {
            if (dentistRepository.existsByEmail(userResponseDto.email()))
                throw new DentistWithEmailAlreadyExist("Dentist with email " + userResponseDto.email() + " already exists");
            dentist.setEmail(userResponseDto.email());
        }

        if (userResponseDto.phoneNumber() != null) {
            if (dentistRepository.existsByPhoneNumber(userResponseDto.phoneNumber()))
                throw new DentistWithPhoneNumberAlreadyExist("Dentist with phone number " + userResponseDto.phoneNumber() + " already exists");
            dentist.setPhoneNumber(userResponseDto.phoneNumber());

        }
        dentistRepository.save(dentist);
    }
}
