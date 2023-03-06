package com.example.Doctor.service;

import com.example.Doctor.dao.DoctorRepository;
import com.example.Doctor.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;

    public Doctor saveDoctor(Doctor doctor) {
        String doctorName = doctor.getDoctorName();
        doctorName = "Dr. " + doctorName;
        doctor.setDoctorName(doctorName);
        return repository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    public Doctor getDoctorById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteDoctorById(Integer id) {
        repository.deleteById(id);
    }

    public Doctor updateDoctor(Doctor updatedDoctor) {
        String doctorName = updatedDoctor.getDoctorName();
        doctorName = "Dr. " + doctorName;
        updatedDoctor.setDoctorName(doctorName);
        return repository.save(updatedDoctor);
    }

}
