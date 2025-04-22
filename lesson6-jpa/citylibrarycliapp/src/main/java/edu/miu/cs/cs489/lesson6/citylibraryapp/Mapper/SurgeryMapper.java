package edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.SurgeryRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.SurgeryResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Address;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Surgery;
import org.springframework.stereotype.Component;

@Component
public class SurgeryMapper {

    public Surgery mapToSurgery(SurgeryRequestDto surgeryRequestDto) {
        Surgery surgery = new Surgery();
        surgery.setName(surgeryRequestDto.name());
        Address address = new Address();
        address.setStreet(surgeryRequestDto.street());
        address.setCity(surgeryRequestDto.city());
        address.setState(surgeryRequestDto.state());
        address.setZipCode(surgeryRequestDto.zipCode());
        surgery.setAddress(address);
        return surgery;
    }

    public SurgeryResponseDto mapToSurgeryResponseDto(Surgery surgery) {
        return new SurgeryResponseDto(
                surgery.getId(),
                surgery.getName(),
                surgery.getAddress().getStreet(),
                surgery.getAddress().getCity(),
                surgery.getAddress().getState(),
                surgery.getAddress().getZipCode()
        );

    }


}
