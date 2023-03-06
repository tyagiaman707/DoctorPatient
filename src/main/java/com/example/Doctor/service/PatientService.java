package com.example.Doctor.service;

import com.example.Doctor.dao.PatientRepository;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository repository;

    public void savePatient(Patient patient) {
        repository.save(patient);
    }

    public JSONArray getPatients() {
        List<Patient> patientList = repository.findAll();
        JSONArray patientArr = new JSONArray();
        for (Patient patient: patientList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patient.getPatientId());
            jsonObject.put("patient_name", patient.getPatientName());
            jsonObject.put("age", patient.getAge());
            jsonObject.put("phone_number", patient.getPhoneNumber());
            jsonObject.put("disease_type", patient.getDiseaseType());
            jsonObject.put("gender", patient.getGender());
            jsonObject.put("admit_date", patient.getAdmitDate());
            jsonObject.put("doctorId", patient.getDoctor().getDoctorId());
            patientArr.put(jsonObject);
        }
        return patientArr;
    }

    public JSONArray getPatientById(String patientId) {
        Patient patient = repository.findById(Integer.valueOf(patientId)).orElse(null);
        JSONArray patientArr = new JSONArray();
        if (patient != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patient.getPatientId());
            jsonObject.put("patient_name", patient.getPatientName());
            jsonObject.put("age", patient.getAge());
            jsonObject.put("phone_number", patient.getPhoneNumber());
            jsonObject.put("disease_type", patient.getDiseaseType());
            jsonObject.put("gender", patient.getGender());
            jsonObject.put("admit_date", patient.getAdmitDate());
            jsonObject.put("doctorId", patient.getDoctor().getDoctorId());
            patientArr.put(jsonObject);
        }
        return patientArr;
    }

    public JSONArray getPatientsByDoctorId(String doctorId) {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(Integer.valueOf(doctorId));
        List<Patient> patientList = repository.findByDoctor(doctor);
        JSONArray patientArr = new JSONArray();
        for (Patient patient: patientList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patient.getPatientId());
            jsonObject.put("patient_name", patient.getPatientName());
            jsonObject.put("age", patient.getAge());
            jsonObject.put("phone_number", patient.getPhoneNumber());
            jsonObject.put("disease_type", patient.getDiseaseType());
            jsonObject.put("gender", patient.getGender());
            jsonObject.put("admit_date", patient.getAdmitDate());
            jsonObject.put("doctorId", patient.getDoctor().getDoctorId());
            patientArr.put(jsonObject);
        }
        return patientArr;
    }
}
