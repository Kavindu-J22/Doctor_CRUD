# Doctor Registration System

A comprehensive Java Spring Boot web application for managing doctor registrations with full CRUD operations.

## Features

- **Complete CRUD Operations**: Create, Read, Update, Delete doctors
- **Professional Web Interface**: Responsive design with Bootstrap 5
- **Form Validation**: Client-side and server-side validation
- **Search Functionality**: Search doctors by name, specialization, or hospital
- **Database Integration**: MySQL database with automatic table creation
- **Modern UI/UX**: Clean, professional interface with icons and animations

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Frontend**: Thymeleaf, Bootstrap 5, HTML5, CSS3, JavaScript
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.9+
- **Dependencies**: Spring Data JPA, Spring Web, Spring Validation, MySQL Connector

## Doctor Entity Fields

The system manages doctors with the following information:

1. **First Name** (Required)
2. **Last Name** (Required)
3. **Email** (Required, Unique)
4. **Phone Number** (Required)
5. **Specialization** (Required)
6. **Years of Experience** (Required)
7. **Hospital/Clinic** (Required)
8. **Date of Birth** (Required)
9. **Address** (Optional)

## Prerequisites

1. **Java 17 or higher**
2. **Maven 3.6 or higher**
3. **MySQL 8.0 or higher**
4. **Web browser** (Chrome, Firefox, Safari, Edge)

## Database Setup

1. **Install MySQL** and start the MySQL service
2. **Create a database** (optional - the application will create it automatically):
   ```sql
   CREATE DATABASE doctor_registration;
   ```
3. **Verify credentials**: The application is configured to use:
   - Username: `root`
   - Password: `Shashini1223@`
   - Database: `doctor_registration` (auto-created)

## Installation & Running

### Method 1: Using Maven (Recommended)

1. **Clone or extract** the project to your desired location
2. **Navigate** to the project directory:
   ```bash
   cd Doctor_CRUD
   ```
3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access the application** at: http://localhost:8080

### Method 2: Using IDE

1. **Import** the project into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. **Run** the main class: `DoctorCrudApplication.java`
3. **Access the application** at: http://localhost:8080

### Method 3: Building JAR

1. **Build the JAR**:
   ```bash
   mvn clean package
   ```
2. **Run the JAR**:
   ```bash
   java -jar target/doctor-crud-0.0.1-SNAPSHOT.jar
   ```
3. **Access the application** at: http://localhost:8080

## Application URLs

- **Home/Doctor List**: http://localhost:8080
- **Add New Doctor**: http://localhost:8080/doctors/new
- **View Doctor**: http://localhost:8080/doctors/view/{id}
- **Edit Doctor**: http://localhost:8080/doctors/edit/{id}
- **Search Doctors**: http://localhost:8080/doctors/search

## Usage Guide

### Adding a New Doctor

1. Click "Add New Doctor" button
2. Fill in all required fields (marked with \*)
3. Click "Add Doctor" to save

### Viewing Doctor Details

1. From the doctors list, click the "View" (eye icon) button
2. View complete doctor information
3. Use quick action buttons for email/phone contact

### Editing a Doctor

1. Click the "Edit" (pencil icon) button from the list or detail view
2. Modify the information
3. Click "Update Doctor" to save changes

### Deleting a Doctor

1. Click the "Delete" (trash icon) button
2. Confirm the deletion in the popup dialog

### Searching Doctors

1. Use the search form at the top of the doctors list
2. Choose search type: Name, Specialization, or Hospital/Clinic
3. Enter search term and click "Search"
4. Click "Clear" to reset the search

## Project Structure

```
Doctor_CRUD/
├── src/main/java/com/doctorregister/doctorcrud/
│   ├── DoctorCrudApplication.java          # Main application class
│   ├── controller/
│   │   ├── DoctorController.java           # Web controller
│   │   └── HomeController.java             # Home redirect controller
│   ├── entity/
│   │   └── Doctor.java                     # Doctor entity model
│   ├── repository/
│   │   └── DoctorRepository.java           # Data access layer
│   └── service/
│       └── DoctorService.java              # Business logic layer
├── src/main/resources/
│   ├── application.properties              # Configuration
│   ├── static/css/
│   │   └── style.css                       # Custom styles
│   └── templates/
│       ├── layout.html                     # Base template
│       └── doctors/
│           ├── list.html                   # Doctor list page
│           ├── form.html                   # Add/Edit form
│           └── view.html                   # Doctor detail page
├── pom.xml                                 # Maven configuration
├── run.bat                                 # Windows batch file to run
└── README.md                               # This file
```

## Configuration

The application configuration is in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/doctor_registration?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Shashini1223@

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

## Troubleshooting

### 🚨 Whitelabel Error Page When Saving Doctor

If you encounter a whitelabel error page when trying to save a doctor, follow these steps:

#### Quick Diagnostic Tools

1. **Health Check**: Visit http://localhost:8080/health
2. **Database Test**: Visit http://localhost:8080/test-db
3. **Check Console**: Look for error messages in the application console

#### Common Causes and Solutions

**1. Database Connection Issues (Most Common)**

- Ensure MySQL is running: `net start mysql` (Windows) or `sudo systemctl start mysql` (Linux)
- Verify credentials: username=`root`, password=`Shashini1223@`
- Test connection manually: `mysql -u root -p`

**2. Form Validation Issues**

- Check all required fields are filled correctly
- Ensure email is unique (not already used by another doctor)
- Verify date of birth is in the past
- Phone number should be 10-15 digits

**3. Database Creation Issues**

- The application auto-creates the database, but if it fails:
  ```sql
  CREATE DATABASE IF NOT EXISTS doctor_registration;
  ```

### Other Common Issues

#### Database Connection Issues

- Ensure MySQL is running
- Verify username/password in `application.properties`
- Check if port 3306 is available

#### Port Already in Use

- Change the port in `application.properties`:
  ```properties
  server.port=8081
  ```

#### Maven Issues

- Ensure Maven is installed and in PATH
- Try: `mvn clean install` to resolve dependencies

### Detailed Troubleshooting

For comprehensive troubleshooting steps, see [TROUBLESHOOTING.md](TROUBLESHOOTING.md)

## Features Implemented

✅ **Complete CRUD Operations**
✅ **Form Validation** (Client & Server-side)
✅ **Responsive Design** (Mobile-friendly)
✅ **Search Functionality**
✅ **Professional UI/UX**
✅ **Database Integration**
✅ **Error Handling**
✅ **Success/Error Messages**
✅ **Confirmation Dialogs**
✅ **Auto-generated Timestamps**

## Future Enhancements

- User authentication and authorization
- Doctor photo upload
- Export to PDF/Excel
- Advanced filtering options
- Email notifications
- Appointment scheduling integration

## Support

For issues or questions, please check:

1. Database connection and credentials
2. Java and Maven versions
3. Port availability (8080)
4. MySQL service status

---

**Doctor Registration System** - A complete CRUD web application built with Spring Boot and modern web technologies.
