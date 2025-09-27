package com.doctorregister.doctorcrud;

import com.doctorregister.doctorcrud.entity.Doctor;
import com.doctorregister.doctorcrud.repository.DoctorRepository;
import com.doctorregister.doctorcrud.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DoctorCrudApplicationTests {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
        assertNotNull(doctorService);
        assertNotNull(doctorRepository);
    }

    @Test
    void testDoctorCrudOperations() {
        // Create a test doctor
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john.doe@test.com");
        doctor.setPhoneNumber("1234567890");
        doctor.setSpecialization("Cardiology");
        doctor.setYearsOfExperience(10);
        doctor.setHospitalClinic("Test Hospital");
        doctor.setDateOfBirth(LocalDate.of(1980, 1, 1));
        doctor.setAddress("123 Test Street");

        // Test Create
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        assertNotNull(savedDoctor.getId());
        assertEquals("John", savedDoctor.getFirstName());
        assertEquals("Doe", savedDoctor.getLastName());

        // Test Read
        Doctor foundDoctor = doctorService.getDoctorByIdOrThrow(savedDoctor.getId());
        assertEquals("john.doe@test.com", foundDoctor.getEmail());
        assertEquals("Cardiology", foundDoctor.getSpecialization());

        // Test Update
        foundDoctor.setSpecialization("Neurology");
        foundDoctor.setYearsOfExperience(15);
        Doctor updatedDoctor = doctorService.updateDoctor(foundDoctor.getId(), foundDoctor);
        assertEquals("Neurology", updatedDoctor.getSpecialization());
        assertEquals(15, updatedDoctor.getYearsOfExperience());

        // Test Delete
        Long doctorId = savedDoctor.getId();
        doctorService.deleteDoctor(doctorId);
        assertFalse(doctorService.existsById(doctorId));
    }

    @Test
    void testDoctorValidation() {
        // Test email uniqueness
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Jane");
        doctor1.setLastName("Smith");
        doctor1.setEmail("jane.smith@test.com");
        doctor1.setPhoneNumber("9876543210");
        doctor1.setSpecialization("Pediatrics");
        doctor1.setYearsOfExperience(5);
        doctor1.setHospitalClinic("Children's Hospital");
        doctor1.setDateOfBirth(LocalDate.of(1985, 5, 15));

        Doctor savedDoctor1 = doctorService.saveDoctor(doctor1);
        assertNotNull(savedDoctor1.getId());

        // Test that email uniqueness is checked
        assertTrue(doctorService.existsByEmail("jane.smith@test.com"));
        assertFalse(doctorService.existsByEmail("nonexistent@test.com"));
    }

    @Test
    void testSearchFunctionality() {
        // Create test doctors
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Alice");
        doctor1.setLastName("Johnson");
        doctor1.setEmail("alice.johnson@test.com");
        doctor1.setPhoneNumber("1111111111");
        doctor1.setSpecialization("Dermatology");
        doctor1.setYearsOfExperience(8);
        doctor1.setHospitalClinic("Skin Care Clinic");
        doctor1.setDateOfBirth(LocalDate.of(1982, 3, 10));

        Doctor doctor2 = new Doctor();
        doctor2.setFirstName("Bob");
        doctor2.setLastName("Wilson");
        doctor2.setEmail("bob.wilson@test.com");
        doctor2.setPhoneNumber("2222222222");
        doctor2.setSpecialization("Orthopedics");
        doctor2.setYearsOfExperience(12);
        doctor2.setHospitalClinic("Bone & Joint Hospital");
        doctor2.setDateOfBirth(LocalDate.of(1978, 7, 20));

        doctorService.saveDoctor(doctor1);
        doctorService.saveDoctor(doctor2);

        // Test search by specialization
        var dermatologists = doctorService.findBySpecialization("Dermatology");
        assertEquals(1, dermatologists.size());
        assertEquals("Alice", dermatologists.get(0).getFirstName());

        // Test search by hospital
        var boneJointDoctors = doctorService.findByHospitalClinic("Bone & Joint");
        assertEquals(1, boneJointDoctors.size());
        assertEquals("Bob", boneJointDoctors.get(0).getFirstName());

        // Test search by name
        var aliceDoctors = doctorService.findByName("Alice");
        assertEquals(1, aliceDoctors.size());
        assertEquals("Johnson", aliceDoctors.get(0).getLastName());
    }
}
