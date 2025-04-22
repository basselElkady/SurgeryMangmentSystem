package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

public record SurgeryResponseDto(
    Long id,
    String name,
    String street,
    String city,
    String state,
    String zipCode
) {
}
