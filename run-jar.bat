@echo off
echo ========================================
echo Doctor Registration System - JAR Runner
echo ========================================
echo.

REM Check if JAR file exists
if not exist "target\doctor-crud-0.0.1-SNAPSHOT.jar" (
    echo ERROR: JAR file not found!
    echo Please run: mvn clean package -DskipTests
    echo.
    pause
    exit /b 1
)

echo Starting Doctor Registration System...
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
