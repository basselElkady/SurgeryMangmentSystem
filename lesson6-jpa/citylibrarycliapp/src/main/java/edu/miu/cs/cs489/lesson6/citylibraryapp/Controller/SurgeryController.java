package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.SurgeryRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.SurgeryListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.SurgeryResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryNotFoundWithName;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryWithNameAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.SurgeryMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.SurgeryService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surgery")
@AllArgsConstructor
public class SurgeryController {

    private SurgeryService surgeryService;
    private SurgeryMapper surgeryMapper;

    @PostMapping
    public ResponseEntity<Void> addSurgery(@RequestBody @NotBlank SurgeryRequestDto surgeryRequestDto) throws SurgeryWithNameAlreadyExist {
        surgeryService.addSurgery(surgeryRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSurgery(@RequestBody @NotBlank SurgeryResponseDto surgeryResponseDto) throws SurgeryWithNameAlreadyExist, SurgeryNotFoundWithId {
        surgeryService.updateSurgery(surgeryResponseDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSurgery(@RequestParam String name) throws SurgeryNotFoundWithName {
        surgeryService.deleteSurgery(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SurgeryResponseDto> getSurgery(@RequestParam String name) throws SurgeryNotFoundWithName {
        SurgeryResponseDto surgeryResponseDto = surgeryService.getSurgery(name);
        return ResponseEntity.ok(surgeryResponseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<SurgeryListResponse> getAllSurgeries() {
        SurgeryListResponse surgeryListResponse = surgeryService.getAllSurgeries();
        return ResponseEntity.ok(surgeryListResponse);
    }



}
