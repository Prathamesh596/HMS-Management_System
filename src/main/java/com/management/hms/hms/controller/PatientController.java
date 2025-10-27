package com.management.hms.hms.controller;


import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.PatientRepository;
import com.management.hms.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    // ADMIN can create patient
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Patient addPatient(@RequestBody Patient patient){
        return patientRepository.save(patient);
    }

    // ADMIN and DOCTOR can view all patients
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    // View single patient
//    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
//    @GetMapping("/{id}")
//    public Patient getPatient(@PathVariable Long id){
//        return patientRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Patient not found"));
//    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }

    // ADMIN can update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setAge(patientDetails.getAge());
        patient.setGender(patientDetails.getGender());
        patient.setMedicalHistory(patientDetails.getMedicalHistory());
        return patientRepository.save(patient);
    }

    // ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable Long id){
        patientRepository.deleteById(id);
        return "Patient deleted successfully";
    }
}
