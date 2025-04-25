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

    public Admin(String username, String password, String s, String s1, String email, String s2, Role role) {

        super(username, password, s, s1, email, s2, role);}
}
