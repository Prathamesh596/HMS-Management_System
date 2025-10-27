package com.management.hms.hms.service;

import com.management.hms.hms.entity.MedicalRecord;
import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.MedicalRecordRepository;
import com.management.hms.hms.repository.DoctorRepository;
import com.management.hms.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository recordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public MedicalRecord createRecord(MedicalRecord record){
        Patient patient = patientRepository.findById(record.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(record.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        record.setPatient(patient);
        record.setDoctor(doctor);
        return recordRepository.save(record);
    }

    public List<MedicalRecord> getAllRecords(){
        return recordRepository.findAll();
    }

//    public MedicalRecord getRecordById(Long id){
//        return recordRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Record not found"));
//    }

    public MedicalRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record with id " + id + " not found"));
    }

    public MedicalRecord updateRecord(Long id, MedicalRecord updatedRecord){
        MedicalRecord record = getRecordById(id);
        record.setDiagnosis(updatedRecord.getDiagnosis());
        record.setPrescription(updatedRecord.getPrescription());
        record.setVisitDate(updatedRecord.getVisitDate());
        return recordRepository.save(record);
    }

    public void deleteRecord(Long id){
        recordRepository.deleteById(id);
    }
}
