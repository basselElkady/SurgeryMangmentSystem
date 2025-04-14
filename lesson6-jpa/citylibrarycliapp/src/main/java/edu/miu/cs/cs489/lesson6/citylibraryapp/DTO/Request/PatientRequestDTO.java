package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request;

public record PatientRequestDTO(

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
