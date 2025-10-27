package com.management.hms.hms.controller;

import com.management.hms.hms.entity.MedicalRecord;
import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.MedicalRecordRepository;
import com.management.hms.hms.repository.DoctorRepository;
import com.management.hms.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository recordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // DOCTOR can create record
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public MedicalRecord addRecord(@RequestBody MedicalRecord record){
        Patient patient = patientRepository.findById(record.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(record.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        record.setPatient(patient);
        record.setDoctor(doctor);
        return recordRepository.save(record);
    }

    // All roles can view records
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping
    public List<MedicalRecord> getAllRecords(){
        return recordRepository.findAll();
    }

    // View single record
//    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
//    @GetMapping("/{id}")
//    public MedicalRecord getRecord(@PathVariable Long id){
//        return recordRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
//    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("/{id}")
    public MedicalRecord getRecord(@PathVariable Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id " + id));
    }

    // DOCTOR can update record
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}")
    public MedicalRecord updateRecord(@PathVariable Long id, @RequestBody MedicalRecord recordDetails){
        MedicalRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setDiagnosis(recordDetails.getDiagnosis());
        record.setPrescription(recordDetails.getPrescription());
        record.setVisitDate(recordDetails.getVisitDate());
        return recordRepository.save(record);
    }

    // DOCTOR can delete
    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id){
        recordRepository.deleteById(id);
        return "Record deleted successfully";
    }
}
