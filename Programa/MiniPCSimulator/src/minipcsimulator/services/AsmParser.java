package minipcsimulator.services;

import java.util.ArrayList;
import java.util.Arrays;

public class AsmParser {
    private static int numberLine = 0;

    public static boolean verifySyntax(ArrayList<String> lines) {
        AsmParser.numberLine = 0;
        for (String line : lines) {
            AsmParser.numberLine++;
            if (!line.trim().isEmpty() && !isValidInstruction(line)) {
                throw new RuntimeException("Error de sintaxis en la linea " + AsmParser.numberLine);
            }
        }
        return true;
    }

    private static ArrayList<String> getLineArray(String line) {
        // esto divide el array por espacios y comas
        return new ArrayList<>(Arrays.asList(line.trim().split("[,\\s]+")));
    }

    public static boolean isValidRegister(String register) {
        ArrayList<String> validRegisters = new ArrayList<>(Arrays.asList(
            "AX", "BX", "CX", "DX"
        ));
        return validRegisters.contains(register);
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

        if (!validInstructions.contains(parts.get(0))) return false;


        switch (parts.get(0)) {
            case "LOAD":
            case "STORE":
            case "ADD":
            case "SUB":
                return parts.size() == 2 && isValidRegister(parts.get(1).trim());
            case "MOV":
                return parts.size() == 3 && isValidRegister(parts.get(1).trim()) && isValidNumber(parts.get(2).trim());
            default:
                return false;
        }
    }
}
