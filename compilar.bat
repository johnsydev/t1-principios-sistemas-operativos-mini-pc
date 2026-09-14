@echo off
if not exist "Programa/MiniPCSimulator/build/classes" mkdir "Programa/MiniPCSimulator/build/classes"
dir /s /b "Programa/MiniPCSimulator\src\*.java" > sources.txt
javac -d "Programa/MiniPCSimulator/build/classes" -cp "Programa/MiniPCSimulator/lib/flatlaf-3.7.2.jar" @sources.txt
del sources.txt