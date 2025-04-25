package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

public record PatientResponseDto(

        String username,
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
