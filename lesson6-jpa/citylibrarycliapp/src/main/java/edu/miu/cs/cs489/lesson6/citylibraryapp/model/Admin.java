package edu.miu.cs.cs489.lesson6.citylibraryapp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Admin extends User {
    public Admin(String firstName, String lastName, String email, String phoneNumber, Role role) {

        super(firstName, lastName, email, phoneNumber, role);
    }
}
