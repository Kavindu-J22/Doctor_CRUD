package com.doctorregister.doctorcrud.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        String errorMessage = "An unexpected error occurred";
        String errorDetails = "";
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "Page not found";
                errorDetails = "The requested page could not be found.";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Internal server error";
                errorDetails = "There was an error processing your request.";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "Access denied";
                errorDetails = "You don't have permission to access this resource.";
            }
        }
        
        if (exception != null) {
            Throwable throwable = (Throwable) exception;
            errorDetails = throwable.getMessage();
            
            // Check for common database connection errors
            if (throwable.getMessage() != null) {
                String msg = throwable.getMessage().toLowerCase();
                if (msg.contains("connection") || msg.contains("database") || msg.contains("mysql")) {
                    errorMessage = "Database Connection Error";
                    errorDetails = "Unable to connect to the database. Please ensure MySQL is running and the credentials are correct.";
                } else if (msg.contains("validation") || msg.contains("constraint")) {
                    errorMessage = "Validation Error";
                    errorDetails = "Please check that all required fields are filled correctly.";
                }
            }
        }
        
        if (message != null) {
            errorDetails = message.toString();
        }
        
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorDetails", errorDetails);
        model.addAttribute("statusCode", status);
        
        return "error";
    }
}
