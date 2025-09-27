package com.doctorregister.doctorcrud.repository;

import com.doctorregister.doctorcrud.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    // Find doctor by email
    Optional<Doctor> findByEmail(String email);
    
    // Find doctors by specialization
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    
    // Find doctors by hospital/clinic
    List<Doctor> findByHospitalClinicContainingIgnoreCase(String hospitalClinic);
    
    // Find doctors by first name or last name
    @Query("SELECT d FROM Doctor d WHERE " +
           "LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByFirstNameOrLastNameContainingIgnoreCase(@Param("name") String name);
    
    // Find doctors by years of experience range
    List<Doctor> findByYearsOfExperienceBetween(Integer minYears, Integer maxYears);
    
    // Find doctors ordered by creation date (newest first)
    List<Doctor> findAllByOrderByCreatedAtDesc();
    
    // Check if email exists (for validation)
    boolean existsByEmail(String email);
    
    // Custom query to search doctors by multiple criteria
    @Query("SELECT d FROM Doctor d WHERE " +
           "(:firstName IS NULL OR LOWER(d.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
           "(:lastName IS NULL OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
           "(:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
           "(:hospitalClinic IS NULL OR LOWER(d.hospitalClinic) LIKE LOWER(CONCAT('%', :hospitalClinic, '%')))")
    List<Doctor> findDoctorsByCriteria(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName,
                                      @Param("specialization") String specialization,
                                      @Param("hospitalClinic") String hospitalClinic);
}
