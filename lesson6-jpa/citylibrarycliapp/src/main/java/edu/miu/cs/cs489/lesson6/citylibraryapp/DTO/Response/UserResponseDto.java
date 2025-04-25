package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

public record UserResponseDto(

    String oldUserName,
    String userName,
    String firstName,
    String lastName,
    String email,
    String phoneNumber
) {
}
