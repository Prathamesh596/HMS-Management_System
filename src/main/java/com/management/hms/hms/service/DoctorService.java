package com.management.hms.hms.service;


import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

//    public Doctor getDoctorById(Long id){
//        return doctorRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Doctor not found"));
//    }
public Doctor getDoctorById(Long id) {
    return doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with id " + id + " not found"));
}

    public Doctor updateDoctor(Long id, Doctor updatedDoctor){
        Doctor doctor = getDoctorById(id);
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setAvailability(updatedDoctor.getAvailability());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }
}
