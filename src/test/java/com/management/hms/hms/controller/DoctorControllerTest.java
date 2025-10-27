package com.management.hms.hms.controller;

import com.management.hms.hms.entity.Doctor;
import com.management.hms.hms.repository.DoctorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("GET /api/doctors - returns list")
    void getAllDoctors() throws Exception {
    // build users for doctors
    com.management.hms.hms.entity.User u1 = new com.management.hms.hms.entity.User();
    u1.setId(11L);
    u1.setName("Doc A");

    com.management.hms.hms.entity.User u2 = new com.management.hms.hms.entity.User();
    u2.setId(12L);
    u2.setName("Doc B");

    Doctor d1 = new Doctor();
    d1.setId(1L);
    d1.setUser(u1);
    d1.setSpecialization("Cardiology");

    Doctor d2 = new Doctor();
    d2.setId(2L);
    d2.setUser(u2);
    d2.setSpecialization("Dermatology");

    given(doctorRepository.findAll()).willReturn(List.of(d1, d2));

    mockMvc.perform(get("/api/doctors").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].user.name").value("Doc A"))
        .andExpect(jsonPath("$[1].specialization").value("Dermatology"));
    }
}
