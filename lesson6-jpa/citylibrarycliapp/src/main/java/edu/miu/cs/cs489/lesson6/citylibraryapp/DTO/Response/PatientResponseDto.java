package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

public record PatientResponseDto(

        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String street,
        String city,
        String state,
        String zipCode

) {
}
