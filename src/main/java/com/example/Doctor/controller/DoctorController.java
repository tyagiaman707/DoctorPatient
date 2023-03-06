package com.example.Doctor.controller;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.service.DoctorService;

import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(value = "/doctor")
    public ResponseEntity<String> saveDoctor(@RequestBody String requestDoctor) {
        JSONObject json = new JSONObject(requestDoctor);
        List<String> validationList = validateDoctor(json);

        if (validationList.isEmpty()) {
            Doctor doctor = setDoctor(json);
            doctorService.saveDoctor(doctor);
            return new ResponseEntity<>("Doctor saved", HttpStatus.CREATED);
        } else {
            String[] answer = Arrays.copyOf(
                    validationList.toArray(), validationList.size(), String[].class);

            return new ResponseEntity<>("Please pass these mandatory parameters- " +
                    Arrays.toString(answer), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Integer id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/doctor")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(doctors);
        }
    }

    @PutMapping(value = "/doctor/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable("id") Integer id, @RequestBody String requestDoctor) {
        JSONObject json = new JSONObject(requestDoctor);
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            List<String> validationList = validateDoctor(json);

            if (validationList.isEmpty()) {
                Doctor updatedDoctor = setDoctor(json);
                updatedDoctor.setDoctorId(id);
                doctorService.saveDoctor(updatedDoctor);
                return new ResponseEntity<>("Doctor updated", HttpStatus.OK);
            } else {
                String[] answer = Arrays.copyOf(
                        validationList.toArray(), validationList.size(), String[].class);

                return new ResponseEntity<>("Please pass these mandatory parameters- " +
                        Arrays.toString(answer), HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/doctor/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable("id") Integer id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            doctorService.deleteDoctorById(id);
            return new ResponseEntity<>("Doctor deleted", HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private List<String> validateDoctor(JSONObject json) {
        List<String> errorList = new ArrayList<>();

        if (!json.has("doctorName")) {
            errorList.add("doctorName");
        }

        if (!json.has("specializedIn")) {
            errorList.add("specializedIn");
        }

        return errorList;
    }

    private Doctor setDoctor(JSONObject json) {
        Doctor doctor = new Doctor();

        if (json.has("doctorId")) {
            String doctorId = json.getString("doctorId");
            doctor.setDoctorId(Integer.valueOf(doctorId));
        }

        String doctorName = json.getString("doctorName");
        doctor.setDoctorName(doctorName);

        if (json.has("specializedIn")) {
            String specializedIn = json.getString("specializedIn");
            doctor.setSpecializedIn(specializedIn);
        }

        return doctor;
    }
}