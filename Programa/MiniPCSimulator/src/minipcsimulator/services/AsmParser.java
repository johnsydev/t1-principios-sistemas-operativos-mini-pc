package minipcsimulator.services;

import java.util.ArrayList;
import java.util.Arrays;

public class AsmParser {
    private static int numberLine = 0;
    private static ArrayList<ArrayList<String>> newLines = new ArrayList<>();

    public static ArrayList<ArrayList<String>> verifySyntax(ArrayList<String> lines) {
        AsmParser.numberLine = 0;
        AsmParser.newLines.clear();
        for (String line : lines) {
            AsmParser.numberLine++;
            if (line.trim().isEmpty()) {
                continue; 
            }
            if (!isValidInstruction(line)) {
                throw new RuntimeException("Error de sintaxis en la linea " + AsmParser.numberLine);
            }
        }
        return AsmParser.newLines;
    }

    private static ArrayList<String> getLineArray(String line) {
        // esto divide el array por espacios y comas
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.trim().split("[,\\s]+")));
        AsmParser.newLines.add(parts);
        return parts;
    }

    public static boolean isValidRegister(String register) {
        ArrayList<String> validRegisters = new ArrayList<>(Arrays.asList(
            "AX", "BX", "CX", "DX"
        ));
        if (!validRegisters.contains(register)) {
            throw new RuntimeException("Error de sintaxis: Registro no reconocido en la línea " + AsmParser.numberLine);
        }
        return true;
    }

    public static boolean isValidNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidInstruction(String instruction) {
        ArrayList<String> parts = getLineArray(instruction);
        if (parts.isEmpty() || parts.size() < 2) return false;

        ArrayList<String> validInstructions = new ArrayList<>(Arrays.asList(
        "LOAD", "STORE", "ADD", "SUB", "MOV"
        ));

        if (!validInstructions.contains(parts.get(0))) {
            throw new RuntimeException("Error de sintaxis: Instrucción no reconocida en la línea " + AsmParser.numberLine);
        }

        switch (parts.get(0)) {
            case "LOAD":
            case "STORE":
            case "ADD":
            case "SUB":
                if (parts.size() == 2 && isValidRegister(parts.get(1).trim())) {
                    return true;
                }
                else {
                    throw new RuntimeException("Error de sintaxis: Instrucción " + parts.get(0) + " requiere un registro válido en la línea " + AsmParser.numberLine);
                }
            case "MOV":
                if (parts.size() == 3 && isValidRegister(parts.get(1).trim()) && isValidNumber(parts.get(2).trim())) {
                    return true;
                }
                else {
                    throw new RuntimeException("Error de sintaxis: Instrucción " + parts.get(0) + " requiere un registro y un valor numérico válidos en la línea " + AsmParser.numberLine);
                }
            default:
                return false;
        }
    }
}
