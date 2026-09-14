package minipcsimulator.services;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que se encarga de parsear y verificar la sintaxis de las instrucciones en .asm.
 * @author johnsydev
 */
public class AsmParser {
    private static int numberLine = 0;
    private static ArrayList<ArrayList<String>> newLines = new ArrayList<>();

    /**
     * Verifica la sintaxis de las instrucciones en .asm y devuelve un ArrayList de ArrayLists con las instrucciones parseadas.
     * @param lines ArrayList de líneas de instrucciones en .asm.
     * @return ArrayList de ArrayLists con las instrucciones parseadas (en string).
     */
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

    /**
     * Divide una linea en un ArrayList de partes.
     * @param line La linea de código a dividir.
     * @return Un ArrayList de partes de la linea dividida por cada componente de la instrucción.
     */
    private static ArrayList<String> getLineArray(String line) {
        // esto divide el array por espacios y comas
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.trim().split("[,\\s]+")));
        AsmParser.newLines.add(parts);
        return parts;
    }

    /**
     * Verifica si un registro es válido.
     * @param register El registro a verificar.
     * @return true si el registro es válido, false en caso contrario.
    */
    public static boolean isValidRegister(String register) {
        ArrayList<String> validRegisters = new ArrayList<>(Arrays.asList(
            "AX", "BX", "CX", "DX"
        ));
        if (!validRegisters.contains(register)) {
            throw new RuntimeException("Error de sintaxis: Registro no reconocido en la línea " + AsmParser.numberLine);
        }
        return true;
    }

    /**
     * Verifica si un número es válido.
     * @param number El número a verificar.
     * @return true si el número es válido, false en caso contrario.
     */
    public static boolean isValidNumber(String number) {
        try {
            int num = Integer.parseInt(number);
            if (num < -127 || num > 127) {
                throw new RuntimeException("Error de sintaxis: Valor numérico fuera de rango soportado (-127 a 127) en la línea " + AsmParser.numberLine);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica si una instrucción es válida.
     * @param instruction La instrucción a verificar.
     * @return true si la instrucción es válida, false en caso contrario.
     */
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
