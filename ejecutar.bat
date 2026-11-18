@echo off
rem Compila y ejecuta la app sin NetBeans.
rem Requiere JDK 17+ en el PATH y MySQL en localhost (usuario root, contrasena admin).
cd /d "%~dp0"
if not exist out mkdir out
dir /s /b src\*.java > out\lista-fuentes.txt
javac -encoding UTF-8 -cp "lib\*" -d out @out\lista-fuentes.txt || pause
java -cp "out;lib\*" main.app
