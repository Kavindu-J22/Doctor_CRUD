@echo off
echo ========================================
echo Doctor Registration System - Build and Run
echo ========================================
echo.

echo Step 1: Building the project...
mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo Step 2: Starting the application...
echo.
echo NOTE: If you see database connection errors:
echo 1. Ensure MySQL is running
echo 2. Check credentials: username=root, password=Shashini1223@
echo 3. Visit http://localhost:8080/test-db to test database connection
echo 4. Visit http://localhost:8080/health for health check
echo.

java -jar target\doctor-crud-0.0.1-SNAPSHOT.jar

echo.
echo Application stopped.
pause
