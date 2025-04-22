package edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response;

import java.util.List;

public record SurgeryListResponse(
        List<SurgeryResponseDto> surgeryList
) {
}
