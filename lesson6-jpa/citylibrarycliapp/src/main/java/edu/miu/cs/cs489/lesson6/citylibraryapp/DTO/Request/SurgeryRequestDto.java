package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request;

public record SurgeryRequestDto(
        String name,
        String street,
        String city,
        String state,
        String zipCode
) {
}
