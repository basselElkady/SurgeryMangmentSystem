package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request;

public record UserRequestDto(
    String username,
    String password,
    String firstName,
    String lastName,
    String email,
    String phoneNumber
) {
}
