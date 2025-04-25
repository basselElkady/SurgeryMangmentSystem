package edu.miu.cs.cs489.lesson6.citylibraryapp.Service;


import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Request.UserRequestDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.DTO.Response.UserResponseDto;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithEmailAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Exceptions.AdminExceptions.AdminWithPhoneNumberAlreadyExist;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Mapper.UserMapper;
import edu.miu.cs.cs489.lesson6.citylibraryapp.Server.IMP.AdminServiceImp;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Admin;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImpTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminServiceImp adminService;

    private Admin mockAdmin;
    private UserRequestDto mockRequestDto;
    private UserResponseDto mockResponseDto;

    @BeforeEach
    void setUp() {
        mockAdmin = new Admin();
        mockAdmin.setId(1L);
        mockAdmin.setUsername("admin1");
        mockAdmin.setFirstName("John");
        mockAdmin.setLastName("Doe");
        mockAdmin.setEmail("john.doe@example.com");
        mockAdmin.setPhoneNumber("1234567890");

        mockRequestDto = new UserRequestDto(
                "admin1",
                "password",
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890"
        );

        mockResponseDto = new UserResponseDto(
                "admin1",
                "admin1_updated",
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890"
        );
    }

    // Add Admin Tests

    // Delete Admin Tests
    @Test
    void deleteAdmin_ShouldDeleteAdmin_WhenAdminExists() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.of(mockAdmin));

        // Act
        adminService.deleteAdmin(1L);

        // Assert
        verify(adminRepository, times(1)).delete(mockAdmin);
    }

    @Test
    void deleteAdmin_ShouldThrowRuntimeException_WhenAdminNotFound() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminService.deleteAdmin(1L);
        });
        verify(adminRepository, never()).delete(any());
    }

    // Get Admin Tests
    @Test
    void getAdmin_ShouldReturnAdminDto_WhenAdminExists() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.of(mockAdmin));
        when(userMapper.toAdminDto(mockAdmin)).thenReturn(mockResponseDto);

        // Act
        UserResponseDto result = adminService.getAdmin(1L);

        // Assert
        assertNotNull(result);
        assertEquals(mockResponseDto, result);
        verify(adminRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toAdminDto(mockAdmin);
    }

    @Test
    void getAdmin_ShouldThrowRuntimeException_WhenAdminNotFound() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminService.getAdmin(1L);
        });
        verify(userMapper, never()).toAdminDto(any());
    }

    // Update Admin Tests

    @Test
    void updateAdmin_ShouldThrowRuntimeException_WhenAdminNotFound() {
        // Arrange
        when(adminRepository.findByUsername("nonexistent")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminService.updateAdmin(new UserResponseDto(
                    "nonexistent",
                    "updated",
                    "John",
                    "Doe",
                    "email@example.com",
                    "1234567890"
            ));
        });
        verify(adminRepository, never()).save(any());
    }

    @Test
    void updateAdmin_ShouldThrowRuntimeException_WhenEmailAlreadyExists() {
        // Arrange
        Admin existingAdmin = new Admin();
        existingAdmin.setUsername("admin1");

        when(adminRepository.findByUsername("admin1")).thenReturn(existingAdmin);
        when(adminRepository.existsByEmail("existing.email@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminService.updateAdmin(new UserResponseDto(
                    "admin1",
                    "admin1",
                    "John",
                    "Doe",
                    "existing.email@example.com",
                    "1234567890"
            ));
        });
        verify(adminRepository, never()).save(any());
    }

    @Test
    void updateAdmin_ShouldThrowRuntimeException_WhenPhoneNumberAlreadyExists() {
        // Arrange
        Admin existingAdmin = new Admin();
        existingAdmin.setUsername("admin1");

        when(adminRepository.findByUsername("admin1")).thenReturn(existingAdmin);
        when(adminRepository.existsByEmail("new.email@example.com")).thenReturn(false);
        when(adminRepository.existsByPhoneNumber("existing.phone")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminService.updateAdmin(new UserResponseDto(
                    "admin1",
                    "admin1",
                    "John",
                    "Doe",
                    "new.email@example.com",
                    "existing.phone"
            ));
        });
        verify(adminRepository, never()).save(any());
    }
}