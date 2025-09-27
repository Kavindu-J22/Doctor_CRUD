@echo off
echo ========================================
echo MySQL Service Manager
echo ========================================
echo.

echo Checking MySQL service status...
sc query mysql >nul 2>&1
if %errorlevel% equ 0 (
    echo MySQL service found: mysql
    net start mysql
    goto :test_connection
)

sc query mysql80 >nul 2>&1
if %errorlevel% equ 0 (
    echo MySQL service found: mysql80
    net start mysql80
    goto :test_connection
)

sc query "MySQL Server" >nul 2>&1
if %errorlevel% equ 0 (
    echo MySQL service found: MySQL Server
    net start "MySQL Server"
    goto :test_connection
)

echo.
echo ERROR: MySQL service not found!
echo.
echo Please check your MySQL installation:
echo 1. Open Services (services.msc)
echo 2. Look for MySQL service
echo 3. Start it manually
echo 4. Or reinstall MySQL
echo.
pause
exit /b 1

:test_connection
echo.
echo Testing MySQL connection...
echo Please enter MySQL root password when prompted: Shashini1223@
echo.

mysql -u root -p --execute="SHOW DATABASES; CREATE DATABASE IF NOT EXISTS doctor_registration; USE doctor_registration; SHOW TABLES;"

if %errorlevel% equ 0 (
    echo.
    echo ✓ MySQL connection successful!
    echo ✓ Database 'doctor_registration' is ready
    echo.
    echo You can now run the application with:
    echo   mvn spring-boot:run
    echo   OR
    echo   java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar
) else (
    echo.
    echo ✗ MySQL connection failed!
    echo Please check:
    echo 1. MySQL root password: Shashini1223@
    echo 2. MySQL is running on port 3306
    echo 3. No firewall blocking the connection
)

echo.
pause
