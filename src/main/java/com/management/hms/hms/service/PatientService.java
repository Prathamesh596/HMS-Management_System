package com.management.hms.hms.service;

import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

//    public Patient getPatientById(Long id){
//        return patientRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Patient not found"));
//    }
    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with id " + id + " not found"));
}


    public Patient updatePatient(Long id, Patient updatedPatient){
        Patient patient = getPatientById(id);
        patient.setAge(updatedPatient.getAge());
        patient.setGender(updatedPatient.getGender());
        patient.setMedicalHistory(updatedPatient.getMedicalHistory());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id){
        patientRepository.deleteById(id);
    }
}
