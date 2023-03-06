package com.example.Doctor.controller;
import com.example.Doctor.dao.DoctorRepository;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import com.example.Doctor.service.PatientService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class PatientController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService service;

    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patientRequest) {

        JSONObject json = new JSONObject(patientRequest);
        Patient patient = setPatient(json);
        service.savePatient(patient);

        return "Saved patient";

    }

    private Patient setPatient(JSONObject json) {

        Patient patient = new Patient();

        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));

        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);

        String doctorId = json.getString("doctorId");
        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctor(doctor);

        return patient;


    }


    @GetMapping(value = "/patient")
    public ResponseEntity getPatients(@Nullable @RequestParam String doctorId,
                                      @Nullable @RequestParam String patientId) {

        JSONArray patientDetails = service.getPatients();
        //both null- all patients
        if (doctorId == null && patientId == null) {
            patientDetails = service.getPatients();
        }
        //doctorId null- get by patient Id
        else if (doctorId == null) {
            patientDetails = service.getPatientById(patientId);
        }
        //patientId null- get all patients been treated by doctorId
        else if (patientId == null) {
            patientDetails = service.getPatientsByDoctorId(doctorId);
        } else {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(patientDetails.toString(), HttpStatus.OK);


    }
}