package edu.miu.cs.cs489.lesson6.citylibraryapp.Repository;


import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Admin;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Role;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;


    @Test
    void testExistsByEmail_WhenEmailExists_ShouldReturnTrue() {
        // Arrange
        Admin admin = new Admin(
                "admin1", "password",
                "John", "Doe",
                "john.doe@example.com", "1234567890",
                Role.ADMIN
        );
        entityManager.persist(admin);
        entityManager.flush();

        // Act
        boolean exists = adminRepository.existsByEmail("john.doe@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail_WhenEmailDoesNotExist_ShouldReturnFalse() {
        // Act
        boolean exists = adminRepository.existsByEmail("nonexistent@example.com");

        // Assert
        assertFalse(exists);
    }

    @Test
    void testFindByUsername_WhenUsernameExists_ShouldReturnAdmin() {
        // Arrange
        Admin admin = new Admin(
                "admin1", "password",
                "John", "Doe",
                "john.doe@example.com", "1234567890",
                Role.ADMIN
        );
        entityManager.persist(admin);
        entityManager.flush();

        // Act
        Admin found = adminRepository.findByUsername("admin1");

        // Assert
        assertNotNull(found);
        assertEquals("admin1", found.getUsername());
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
    }

    @Test
    void testFindByUsername_WhenUsernameDoesNotExist_ShouldReturnNull() {
        // Act
        Admin found = adminRepository.findByUsername("nonexistent");

        // Assert
        assertNull(found);
    }

    @Test
    void testExistsByPhoneNumber_WhenPhoneNumberExists_ShouldReturnTrue() {
        // Arrange
        Admin admin = new Admin(
                "admin1", "password",
                "John", "Doe",
                "john.doe@example.com", "1234567890",
                Role.ADMIN
        );
        entityManager.persist(admin);
        entityManager.flush();

        // Act
        boolean exists = adminRepository.existsByPhoneNumber("1234567890");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsByPhoneNumber_WhenPhoneNumberDoesNotExist_ShouldReturnFalse() {
        // Act
        boolean exists = adminRepository.existsByPhoneNumber("9876543210");

        // Assert
        assertFalse(exists);
    }

    @Test
    void testSaveAdmin_ShouldPersistAdminCorrectly() {
        // Arrange
        Admin admin = new Admin(
                "admin1", "password",
                "John", "Doe",
                "john.doe@example.com", "1234567890",
                Role.ADMIN
        );

        // Act
        Admin saved = adminRepository.save(admin);
        Admin found = entityManager.find(Admin.class, saved.getId());

        // Assert
        assertNotNull(found);
        assertEquals("admin1", found.getUsername());
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
        assertEquals("john.doe@example.com", found.getEmail());
        assertEquals("1234567890", found.getPhoneNumber());
        assertEquals(Role.ADMIN, found.getRole());
    }
}