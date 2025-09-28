package com.doctorregister.doctorcrud.controller;

import com.doctorregister.doctorcrud.entity.Doctor;
import com.doctorregister.doctorcrud.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Redirect root to doctors list
     */
    @GetMapping("/")
    public String redirectToList() {
        try {
            return "redirect:/doctors/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    /**
     * Show all doctors
     */
    @GetMapping({ "/list", "" }) // Handle both /doctors/list and /doctors
    public String listDoctors(Model model) {
        try {
            // First, try to get doctors from service
            List<Doctor> doctors = doctorService.getAllDoctors();

            // Log for debugging
            System.out.println("Retrieved " + doctors.size() + " doctors from database");

            // Add attributes to model
            model.addAttribute("doctors", doctors != null ? doctors : new java.util.ArrayList<>());
            model.addAttribute("totalCount", doctors != null ? doctors.size() : 0);

            // Return template name
            return "doctors/list";

        } catch (Exception e) {
            // Log the full exception for debugging
            System.err.println("Error in listDoctors method:");
            e.printStackTrace();

            String errorMessage = "Error loading doctors list: ";
            if (e.getMessage() != null) {
                errorMessage += e.getMessage();
            } else {
                errorMessage += "Unknown error occurred. Please check the server logs.";
            }

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("doctors", new java.util.ArrayList<>());
            model.addAttribute("totalCount", 0);
            return "doctors/list";
        }
    }

    /**
     * Show form to add new doctor
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Add New Doctor");
        return "doctors/form";
    }

    /**
     * Process form to add new doctor
     */
    @PostMapping("/save")
    public String saveDoctor(@Valid @ModelAttribute("doctor") Doctor doctor,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", doctor.getId() == null ? "Add New Doctor" : "Edit Doctor");
            return "doctors/form";
        }

        // Check for duplicate email (for new doctors or when email is changed)
        if (doctor.getId() == null) {
            if (doctorService.existsByEmail(doctor.getEmail())) {
                bindingResult.rejectValue("email", "error.doctor", "Email already exists");
                model.addAttribute("pageTitle", "Add New Doctor");
                return "doctors/form";
            }
        } else {
            if (doctorService.existsByEmailAndIdNot(doctor.getEmail(), doctor.getId())) {
                bindingResult.rejectValue("email", "error.doctor", "Email already exists");
                model.addAttribute("pageTitle", "Edit Doctor");
                return "doctors/form";
            }
        }

        try {
            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            String message = doctor.getId() == null
                    ? "Doctor '" + savedDoctor.getFullName() + "' has been added successfully!"
                    : "Doctor '" + savedDoctor.getFullName() + "' has been updated successfully!";
            redirectAttributes.addFlashAttribute("successMessage", message);
            return "redirect:/doctors/list";
        } catch (Exception e) {
            // Log the full exception for debugging
            e.printStackTrace();

            String errorMessage = "Error saving doctor: ";
            if (e.getMessage() != null) {
                if (e.getMessage().contains("Connection") || e.getMessage().contains("database")) {
                    errorMessage += "Database connection failed. Please ensure MySQL is running.";
                } else if (e.getMessage().contains("Duplicate entry")) {
                    errorMessage += "Email address already exists. Please use a different email.";
                } else {
                    errorMessage += e.getMessage();
                }
            } else {
                errorMessage += "Unknown error occurred. Please check the server logs.";
            }

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("pageTitle", doctor.getId() == null ? "Add New Doctor" : "Edit Doctor");
            return "doctors/form";
        }
    }

    /**
     * Show doctor details
     */
    @GetMapping("/view/{id}")
    public String viewDoctor(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
        if (doctorOpt.isPresent()) {
            model.addAttribute("doctor", doctorOpt.get());
            return "doctors/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found with ID: " + id);
            return "redirect:/doctors/list";
        }
    }

    /**
     * Show form to edit doctor
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
        if (doctorOpt.isPresent()) {
            model.addAttribute("doctor", doctorOpt.get());
            model.addAttribute("pageTitle", "Edit Doctor");
            return "doctors/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found with ID: " + id);
            return "redirect:/doctors/list";
        }
    }

    /**
     * Delete doctor
     */
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
            if (doctorOpt.isPresent()) {
                Doctor doctor = doctorOpt.get();
                doctorService.deleteDoctor(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Doctor '" + doctor.getFullName() + "' has been deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found with ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting doctor: " + e.getMessage());
        }
        return "redirect:/doctors/list";
    }

    /**
     * Test page
     */
    @GetMapping("/test")
    public String testPage() {
        return "test";
    }

    /**
     * Simple test endpoint to check if service works
     */
    @GetMapping("/test-service")
    @ResponseBody
    public String testService() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            return "Service works! Found " + doctors.size() + " doctors.";
        } catch (Exception e) {
            return "Service error: " + e.getMessage();
        }
    }

    /**
     * Simple list page for testing
     */
    @GetMapping("/list-simple")
    public String listDoctorsSimple(Model model) {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
            model.addAttribute("totalCount", doctors.size());
            return "doctors/list-simple";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("doctors", new java.util.ArrayList<>());
            model.addAttribute("totalCount", 0);
            return "doctors/list-simple";
        }
    }

    /**
     * Debug endpoint to test database connection
     */
    @GetMapping("/debug")
    @ResponseBody
    public String debugDoctors() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Database Debug Information</h3>");
            sb.append("<p><strong>Total doctors:</strong> ").append(doctors.size()).append("</p>");

            if (doctors.isEmpty()) {
                sb.append("<p>No doctors found in database.</p>");
            } else {
                sb.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
                sb.append("<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Specialization</th></tr>");
                for (Doctor doctor : doctors) {
                    sb.append("<tr>")
                            .append("<td>").append(doctor.getId()).append("</td>")
                            .append("<td>").append(doctor.getFirstName()).append(" ").append(doctor.getLastName())
                            .append("</td>")
                            .append("<td>").append(doctor.getEmail()).append("</td>")
                            .append("<td>").append(doctor.getPhoneNumber()).append("</td>")
                            .append("<td>").append(doctor.getSpecialization()).append("</td>")
                            .append("</tr>");
                }
                sb.append("</table>");
            }

            sb.append("<br><a href='/doctors/list'>Go to Doctors List</a>");
            sb.append(" | <a href='/doctors/test'>Go to Test Page</a>");

            return sb.toString();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Error occurred:</h3>");
            sb.append("<p><strong>Message:</strong> ").append(e.getMessage()).append("</p>");
            sb.append("<p><strong>Type:</strong> ").append(e.getClass().getSimpleName()).append("</p>");
            sb.append("<h4>Stack Trace:</h4><pre>");
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append(element.toString()).append("\n");
            }
            sb.append("</pre>");
            sb.append("<br><a href='/doctors/test'>Go to Test Page</a>");
            return sb.toString();
        }
    }

    /**
     * Search doctors
     */
    @GetMapping("/search")
    public String searchDoctors(@RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "type", required = false, defaultValue = "name") String searchType,
            Model model) {
        List<Doctor> doctors;

        if (query == null || query.trim().isEmpty()) {
            doctors = doctorService.getAllDoctors();
        } else {
            switch (searchType) {
                case "specialization":
                    doctors = doctorService.findBySpecialization(query);
                    break;
                case "hospital":
                    doctors = doctorService.findByHospitalClinic(query);
                    break;
                default:
                    doctors = doctorService.findByName(query);
                    break;
            }
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("totalCount", doctors.size());
        model.addAttribute("searchQuery", query);
        model.addAttribute("searchType", searchType);
        return "doctors/list";
    }
}
