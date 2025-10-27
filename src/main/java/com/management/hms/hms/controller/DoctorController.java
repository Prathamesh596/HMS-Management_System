package com.management.hms.hms.controller;

import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // ADMIN can add doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor){
        return doctorRepository.save(doctor);
    }

    // ADMIN and DOCTOR can view all doctors
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

//    // View single doctor
//    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
//    @GetMapping("/{id}")
//    public Doctor getDoctor(@PathVariable Long id){
//        return doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
//    }
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
@GetMapping("/{id}")
public Doctor getDoctor(@PathVariable Long id) {
    return doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
}

    // ADMIN can update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setAvailability(doctorDetails.getAvailability());
        return doctorRepository.save(doctor);
    }

    // ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id){
        doctorRepository.deleteById(id);
        return "Doctor deleted successfully";
    }
}
