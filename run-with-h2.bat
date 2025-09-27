@echo off
echo ========================================
echo Doctor Registration System - H2 Database
echo ========================================
echo.
echo This will run the application with H2 in-memory database
echo (Use this if MySQL connection is not working)
echo.

REM Build the project first
echo Building the project...
mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Starting application with H2 database...
echo.
echo Access URLs:
echo - Main Application: http://localhost:8080
echo - H2 Database Console: http://localhost:8080/h2-console
echo   (JDBC URL: jdbc:h2:mem:doctordb, User: sa, Password: [empty])
echo.

java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2

echo.
echo Application stopped.
pause
