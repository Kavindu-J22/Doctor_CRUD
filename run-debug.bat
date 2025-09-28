@echo off
echo ========================================
echo   Doctor Registration System - Debug
echo ========================================
echo.

echo Building application...
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Starting application with H2 database...
echo Access URLs:
echo - Main Application: http://localhost:8080
echo - Debug Endpoint: http://localhost:8080/doctors/debug
echo - H2 Console: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the application
echo.

java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2 --logging.level.com.doctorregister=DEBUG

pause
