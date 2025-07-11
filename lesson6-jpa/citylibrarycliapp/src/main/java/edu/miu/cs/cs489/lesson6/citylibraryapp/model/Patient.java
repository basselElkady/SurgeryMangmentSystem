package edu.miu.cs.cs489.lesson6.citylibraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionType;

import java.util.Deque;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patient extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //private Deque<Appointment> appointments;
    @OrderColumn(name = "appointment_order")
    private List<Appointment> appointments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_appointment_id")
    private Appointment lastAppointment;


//    public Patient( String firstName, String lastName, String email, String phoneNumber,Role role, Address address) {
//        super(firstName, lastName, email, phoneNumber,role);
//        this.address = address;
//    }


    public Patient(String username, String password, String firstName, String lastName, String email, String phoneNumber, Role role, Address address) {

        super(username, password, firstName, lastName, email, phoneNumber, role);
        this.address = address;

    }
}
