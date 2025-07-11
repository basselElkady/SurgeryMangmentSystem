package edu.miu.cs.cs489.lesson6.citylibraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dentist extends User {


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Appointment> appointments;


    public Dentist(String firstName, String lastName, String email, String phoneNumber, Role role) {

        super(firstName, lastName, email, phoneNumber,role);

    }

    public Dentist(String username, String password, String s, String s1, String email, String s2, Role role) {

        super(username, password, s, s1, email, s2, role);
    }
}
