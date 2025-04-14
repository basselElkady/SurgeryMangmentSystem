package edu.miu.cs.cs489.lesson6.citylibraryapp.repository;

import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
