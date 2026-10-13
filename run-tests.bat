@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -cp "lib/*" -d bin src\*.java test\*.java
if errorlevel 1 exit /b 1
java -jar lib\junit-platform-console-standalone-1.10.2.jar execute --class-path bin --scan-class-path --disable-ansi-colors
