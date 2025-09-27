package com.doctorregister.doctorcrud.service;

import com.doctorregister.doctorcrud.entity.Doctor;
import com.doctorregister.doctorcrud.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    
    /**
     * Save a new doctor or update existing doctor
     */
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    /**
     * Get all doctors
     */
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAllByOrderByCreatedAtDesc();
    }
    
    /**
     * Get doctor by ID
     */
    @Transactional(readOnly = true)
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    
    /**
     * Get doctor by ID with exception if not found
     */
    @Transactional(readOnly = true)
    public Doctor getDoctorByIdOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }
    
    /**
     * Update doctor
     */
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor existingDoctor = getDoctorByIdOrThrow(id);
        
        existingDoctor.setFirstName(doctorDetails.getFirstName());
        existingDoctor.setLastName(doctorDetails.getLastName());
        existingDoctor.setEmail(doctorDetails.getEmail());
        existingDoctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        existingDoctor.setSpecialization(doctorDetails.getSpecialization());
        existingDoctor.setYearsOfExperience(doctorDetails.getYearsOfExperience());
        existingDoctor.setHospitalClinic(doctorDetails.getHospitalClinic());
        existingDoctor.setDateOfBirth(doctorDetails.getDateOfBirth());
        existingDoctor.setAddress(doctorDetails.getAddress());
        
        return doctorRepository.save(existingDoctor);
    }
    
    /**
     * Delete doctor by ID
     */
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorByIdOrThrow(id);
        doctorRepository.delete(doctor);
    }
    
    /**
     * Check if doctor exists by ID
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }
    
    /**
     * Check if email already exists
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }
    
    /**
     * Check if email exists for different doctor (for update validation)
     */
    @Transactional(readOnly = true)
    public boolean existsByEmailAndIdNot(String email, Long id) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        return doctor.isPresent() && !doctor.get().getId().equals(id);
    }
    
    /**
     * Find doctors by specialization
     */
    @Transactional(readOnly = true)
    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }
    
    /**
     * Find doctors by hospital/clinic
     */
    @Transactional(readOnly = true)
    public List<Doctor> findByHospitalClinic(String hospitalClinic) {
        return doctorRepository.findByHospitalClinicContainingIgnoreCase(hospitalClinic);
    }
    
    /**
     * Find doctors by name (first or last name)
     */
    @Transactional(readOnly = true)
    public List<Doctor> findByName(String name) {
        return doctorRepository.findByFirstNameOrLastNameContainingIgnoreCase(name);
    }
    
    /**
     * Find doctors by years of experience range
     */
    @Transactional(readOnly = true)
    public List<Doctor> findByExperienceRange(Integer minYears, Integer maxYears) {
        return doctorRepository.findByYearsOfExperienceBetween(minYears, maxYears);
    }
    
    /**
     * Search doctors by multiple criteria
     */
    @Transactional(readOnly = true)
    public List<Doctor> searchDoctors(String firstName, String lastName, 
                                     String specialization, String hospitalClinic) {
        return doctorRepository.findDoctorsByCriteria(firstName, lastName, specialization, hospitalClinic);
    }
    
    /**
     * Get total count of doctors
     */
    @Transactional(readOnly = true)
    public long getTotalDoctorCount() {
        return doctorRepository.count();
    }
}
