package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

public record UserResponseDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phoneNumber
) {
}
