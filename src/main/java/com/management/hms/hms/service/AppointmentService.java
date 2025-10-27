package com.management.hms.hms.service;

import com.management.hms.hms.entity.Appointment;
import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.AppointmentRepository;
import com.management.hms.hms.repository.DoctorRepository;
import com.management.hms.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment createAppointment(Appointment appointment){
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

//    public Appointment getAppointmentById(Long id){
//        return appointmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Appointment not found"));
//    }

    public Appointment getAppointmentById(Long id){
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with id " + id + " not found"));
    }


    public Appointment updateAppointment(Long id, Appointment updatedAppointment){
        Appointment appointment = getAppointmentById(id);
        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        appointment.setStatus(updatedAppointment.getStatus());
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id){
        appointmentRepository.deleteById(id);
    }
}

