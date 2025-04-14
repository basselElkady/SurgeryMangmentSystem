package edu.miu.cs.cs489.lesson6.citylibraryapp.repository;

import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
