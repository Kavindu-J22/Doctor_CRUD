@echo off
echo ========================================
echo   Doctor Registration System - Test & Fix
echo ========================================
echo.

echo Step 1: Building application...
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Starting application with H2 database...
echo.
echo Test URLs to check:
echo - Simple Service Test: http://localhost:8080/doctors/test-service
echo - Simple List Page: http://localhost:8080/doctors/list-simple
echo - Debug Database: http://localhost:8080/doctors/debug
echo - Original List Page: http://localhost:8080/doctors/list
echo - Main Application: http://localhost:8080
echo.
echo Starting application...
echo Press Ctrl+C to stop the application
echo.

start /B java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2 --logging.level.com.doctorregister=DEBUG

echo Waiting for application to start...
timeout /t 10 /nobreak > nul

echo.
echo Testing service endpoint...
curl -s http://localhost:8080/doctors/test-service
echo.
echo.

echo Opening test URLs in browser...
start http://localhost:8080/doctors/test-service
timeout /t 2 /nobreak > nul
start http://localhost:8080/doctors/list-simple
timeout /t 2 /nobreak > nul
start http://localhost:8080/doctors/debug

echo.
echo Check the browser windows that opened.
echo If any show errors, press any key to continue debugging...
pause
