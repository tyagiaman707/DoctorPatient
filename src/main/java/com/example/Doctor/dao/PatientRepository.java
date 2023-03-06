package com.example.Doctor.dao;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByDoctor(Doctor doctor);

}
