@echo off
echo ========================================
echo Doctor Registration System Startup
echo ========================================
echo.
echo Checking prerequisites...
echo.

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)
echo ✓ Maven is available

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17+ and try again
    pause
    exit /b 1
)
echo ✓ Java is available

echo.
echo Starting application...
echo.
echo NOTE: If you see database connection errors:
echo 1. Ensure MySQL is running
echo 2. Check credentials: username=root, password=Shashini1223@
echo 3. Visit http://localhost:8080/test-db to test database connection
echo 4. Visit http://localhost:8080/health for health check
echo.

mvn spring-boot:run

echo.
echo Application stopped.
pause
