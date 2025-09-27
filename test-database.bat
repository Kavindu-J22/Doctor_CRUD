@echo off
echo ========================================
echo Database Connection Tester
echo ========================================
echo.

echo Testing MySQL connection...
echo.

REM Test if MySQL command is available
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL client not found in PATH
    echo Please add MySQL bin directory to PATH
    echo Example: C:\Program Files\MySQL\MySQL Server 8.0\bin
    echo.
    pause
    exit /b 1
)

echo MySQL client found. Testing connection...
echo Please enter password: Shashini1223@
echo.

REM Create a temporary SQL file
echo SHOW DATABASES; > temp_test.sql
echo CREATE DATABASE IF NOT EXISTS doctor_registration; >> temp_test.sql
echo USE doctor_registration; >> temp_test.sql
echo SHOW TABLES; >> temp_test.sql
echo SELECT 'Connection successful!' as Status; >> temp_test.sql

REM Execute the SQL file
mysql -u root -p < temp_test.sql

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✓ SUCCESS: Database connection works!
    echo ✓ Database 'doctor_registration' is ready
    echo ========================================
    echo.
    echo You can now run the application:
    echo 1. Double-click 'build-and-run.bat'
    echo 2. Or run: mvn spring-boot:run
    echo 3. Or run: java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar
    echo.
    echo Application will be available at: http://localhost:8080
) else (
    echo.
    echo ========================================
    echo ✗ FAILED: Database connection failed!
    echo ========================================
    echo.
    echo Possible solutions:
    echo 1. Check if MySQL service is running: net start mysql
    echo 2. Verify password: Shashini1223@
    echo 3. Check if port 3306 is available
    echo 4. Try running as Administrator
    echo.
    echo Alternative: Use H2 database for testing
    echo Double-click 'run-with-h2.bat' to use in-memory database
)

REM Clean up
del temp_test.sql >nul 2>&1

echo.
pause
