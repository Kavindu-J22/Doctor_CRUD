# Doctor Registration System - Troubleshooting Guide

## 🚨 Common Issues and Solutions

### Issue: Whitelabel Error Page When Saving Doctor

This error typically occurs due to one of the following reasons:

#### 1. **Database Connection Issues** (Most Common)

**Symptoms:**

- Whitelabel error page appears when submitting the form
- Application starts but fails when trying to save data

**Solutions:**

1. **Check MySQL Service:**

   ```bash
   # Windows
   net start mysql
   # Or check services.msc for MySQL service

   # Linux/Mac
   sudo systemctl start mysql
   # Or
   brew services start mysql
   ```

2. **Verify Database Credentials:**

   - Username: `root`
   - Password: `Shashini1223@`
   - Port: `3306`

3. **Test Database Connection:**

   - Visit: http://localhost:8080/test-db
   - Or visit: http://localhost:8080/health

4. **Check MySQL Configuration:**
   ```sql
   -- Connect to MySQL and run:
   SHOW DATABASES;
   CREATE DATABASE IF NOT EXISTS doctor_registration;
   USE doctor_registration;
   SHOW TABLES;
   ```

#### 2. **Form Validation Issues**

**Symptoms:**

- Error occurs with specific form data
- Some fields cause errors while others don't

**Solutions:**

1. **Check Required Fields:**

   - First Name (2-50 characters)
   - Last Name (2-50 characters)
   - Email (valid email format, unique)
   - Phone Number (10-15 digits)
   - Specialization (2-100 characters)
   - Years of Experience (0-50)
   - Hospital/Clinic (2-100 characters)
   - Date of Birth (must be in the past)

2. **Email Uniqueness:**

   - Each doctor must have a unique email address
   - Check if the email is already in use

3. **Date Format:**
   - Use the date picker or format: YYYY-MM-DD
   - Date must be in the past

#### 3. **Port Conflicts**

**Symptoms:**

- Application fails to start
- "Port already in use" error

**Solutions:**

1. **Change Application Port:**

   ```properties
   # In application.properties
   server.port=8081
   ```

2. **Kill Process Using Port 8080:**

   ```bash
   # Windows
   netstat -ano | findstr :8080
   taskkill /PID <PID_NUMBER> /F

   # Linux/Mac
   lsof -ti:8080 | xargs kill -9
   ```

## 🔧 Diagnostic Tools

### 1. Health Check Endpoint

Visit: http://localhost:8080/health

This will show:

- Database connection status
- Service layer status
- System information

### 2. Database Test Page

Visit: http://localhost:8080/test-db

This will show:

- Database connection details
- Current doctor count
- Troubleshooting steps

### 3. Application Logs

Check the console output for detailed error messages and stack traces.

## 📋 Step-by-Step Troubleshooting

### Step 1: Verify Prerequisites

1. ✅ Java 17+ installed
2. ✅ Maven 3.6+ installed
3. ✅ MySQL 8.0+ installed and running
4. ✅ Database credentials are correct

### Step 2: Test Database Connection

1. Open command prompt/terminal
2. Connect to MySQL:
   ```bash
   mysql -u root -p
   # Enter password: Shashini1223@
   ```
3. Create database if needed:
   ```sql
   CREATE DATABASE IF NOT EXISTS doctor_registration;
   SHOW DATABASES;
   ```

### Step 3: Start Application with Debugging

1. Use the provided `run.bat` file
2. Or run with debug logging:
   ```bash
   mvn spring-boot:run -Dlogging.level.com.doctorregister.doctorcrud=DEBUG
   ```

### Step 4: Test the Application

1. Visit: http://localhost:8080
2. Try adding a simple doctor with minimal data
3. Check the console for any error messages

### Step 5: Use Diagnostic Endpoints

1. Health check: http://localhost:8080/health
2. Database test: http://localhost:8080/test-db
3. Check for any red flags in the output

## 🐛 Common Error Messages and Solutions

### "Connection refused" or "Communications link failure"

- **Cause:** MySQL is not running
- **Solution:** Start MySQL service

### "Access denied for user 'root'"

- **Cause:** Wrong password or user doesn't exist
- **Solution:** Verify credentials or reset MySQL root password

### "Unknown database 'doctor_registration'"

- **Cause:** Database doesn't exist and auto-creation failed
- **Solution:** Manually create the database or check MySQL permissions

### "Duplicate entry" error

- **Cause:** Email address already exists
- **Solution:** Use a different email address

### "Data truncation" or "Data too long"

- **Cause:** Input data exceeds field limits
- **Solution:** Check field length constraints

## 🔍 Advanced Debugging

### Enable SQL Logging

The application is already configured to show SQL queries. Look for:

```
Hibernate: insert into doctors (address, created_at, date_of_birth, email, first_name, hospital_clinic, last_name, phone_number, specialization, updated_at, years_of_experience) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
```

### Check Database Tables

```sql
USE doctor_registration;
DESCRIBE doctors;
SELECT * FROM doctors;
```

### Verify Application Properties

Check `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/doctor_registration?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Shashini1223@
```

## 📞 Getting Help

If you're still experiencing issues:

1. **Check the console output** for detailed error messages
2. **Visit the diagnostic endpoints** mentioned above
3. **Verify all prerequisites** are met
4. **Try with a minimal doctor record** (just required fields)
5. **Check MySQL error logs** for database-specific issues

## 🎯 Quick Fix Checklist

- [ ] MySQL service is running
- [ ] Database credentials are correct
- [ ] Port 8080 is available
- [ ] All required form fields are filled
- [ ] Email address is unique
- [ ] Date of birth is in the past
- [ ] No special characters causing issues

Most issues are resolved by ensuring MySQL is running with the correct credentials!
