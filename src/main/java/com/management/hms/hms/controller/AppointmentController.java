package com.management.hms.hms.controller;

import com.management.hms.hms.entity.Appointment;
import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.entity.Patient;
import com.management.hms.hms.exception.ResourceNotFoundException;
import com.management.hms.hms.repository.AppointmentRepository;
import com.management.hms.hms.repository.DoctorRepository;
import com.management.hms.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // ADMIN can create appointment
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Appointment addAppointment(@RequestBody Appointment appointment){
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointmentRepository.save(appointment);
    }

    // All roles can view appointments
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    // View single appointment
//    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
//    @GetMapping("/{id}")
//    public Appointment getAppointment(@PathVariable Long id){
//        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
//    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleOtherExceptions(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + ex.getMessage());
        }
    }


    // ADMIN can update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setStatus(appointmentDetails.getStatus());
        return appointmentRepository.save(appointment);
    }

    // ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id){
        appointmentRepository.deleteById(id);
        return "Appointment deleted successfully";
    }
}
