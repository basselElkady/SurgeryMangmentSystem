package edu.miu.cs.cs489.lesson6.citylibraryapp.Server;

import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.SurgeryRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.SurgeryListResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.SurgeryResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryNotFoundWithId;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryNotFoundWithName;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.SurgeryExceptions.SurgeryWithNameAlreadyExist;

public interface SurgeryService {

    void addSurgery(SurgeryRequestDto surgeryRequestDto) throws SurgeryWithNameAlreadyExist;

    void updateSurgery(SurgeryResponseDto surgeryResponseDto) throws SurgeryNotFoundWithId, SurgeryWithNameAlreadyExist;

    void deleteSurgery(String name) throws SurgeryNotFoundWithName;

    SurgeryResponseDto getSurgery(String name) throws SurgeryNotFoundWithName;

    SurgeryListResponse getAllSurgeries();

}
