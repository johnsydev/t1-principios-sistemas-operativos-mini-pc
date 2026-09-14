package minipcsimulator.services;

import java.util.ArrayList;
import minipcsimulator.utils.SystemConfig;

/**
 * Clase que se encarga de convertir instrucciones y registros a binario y viceversa.
 */
public class BinaryUtils {
    // Codigos binarios
    public enum BinaryCodes {
        // Instrucciones
        LOAD("0001", 1, "INSTRUCTION"), 
        STORE("0010", 2, "INSTRUCTION"), 
        ADD("0101", 3, "INSTRUCTION"), 
        SUB("0100", 4, "INSTRUCTION"), 
        MOV("0011", 5, "INSTRUCTION"),

        // Registros
        AX("0001", 5, "REGISTER"),
        BX("0010", 6, "REGISTER"),
        CX("0011", 7, "REGISTER"),
        DX("0100", 8, "REGISTER");

        private final String binaryCode;
        private final int id;
        private final String type;

        // Constructor
        BinaryCodes(String binaryCode, int id, String type) {
            this.binaryCode = binaryCode;
            this.id = id;
            this.type = type;
        }

        public String getBinaryCode() {
            return binaryCode;
        }

        public String getType() {
            return type;
        }

        /**
         * Obtiene el valor binario de un nombre.
         * @param name El nombre de la instrucción o registro.
         * @return El valor binario correspondiente al nombre, o null si no se encuentra.
         */
        public static String getValueOf(String name) {
            try {
                return BinaryCodes.valueOf(name.toUpperCase()).getBinaryCode();
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Obtiene el objeto BinaryCodes correspondiente a un valor binario y un tipo de dato.
         * @param code El valor binario.
         * @param type El tipo de dato (INSTRUCTION o REGISTER).
         * @return El objeto BinaryCodes correspondiente, o null si no se encuentra.
         */
        public static BinaryCodes getByBinaryCode(String code, String type) {
            for (BinaryCodes codes : values()) {
                if (codes.binaryCode.equals(code) && codes.type.equals(type)) {
                    return codes;
                }
            }
            return null;
        }
    }

    /**
     * Convierte un número entero a su representación binaria de 8 bits, considerando el bit de signo.
     * @param number El número entero a convertir.
     * @return La representación binaria del número como una cadena de 8 bits.
     */
    public static String numberToBinary(int number) {
        char bitSigno = '0';
        if (number < 0) {
            bitSigno = '1'; // si es negativo
            number = -number;
        }

        String binarioValor = Integer.toBinaryString(number);
        binarioValor = String.format("%" + (SystemConfig.REGISTER_VALUE_SIZE - 1) + "s", binarioValor).replace(' ', '0'); // rellenar

        // recortar num si es muy grande, solo se toman los 7 bits menos significativos
        if (binarioValor.length() > SystemConfig.REGISTER_VALUE_SIZE - 1) {
            binarioValor = binarioValor.substring(binarioValor.length() - (SystemConfig.REGISTER_VALUE_SIZE - 1));
        }

        return bitSigno + binarioValor;
    }

    /**
     * Convierte una representación binaria de 8 bits a su valor entero, considerando el bit de signo.
     * @param binary La representación binaria como una cadena de 8 bits.
     * @return El valor entero correspondiente al binario.
     */
    public static int binaryToNumber(String binary) {
        if (binary.length() != SystemConfig.REGISTER_VALUE_SIZE) {
            throw new IllegalArgumentException("El binario debe tener una longitud de " + SystemConfig.REGISTER_VALUE_SIZE);
        }

        char bitSign = binary.charAt(0);
        String binaryValue = binary.substring(1);

        int number = Integer.parseInt(binaryValue, 2);
        if (bitSign == '1') {
            number = -number; 
        }

        return number;
    }

    /**
     * Traduce una instrucción a su representación binaria en el sistema.
     * @param instructionParts Un ArrayList de partes de la instrucción (opcode, registro, valor).
     * @return La representación binaria de la instrucción como una cadena.
     */
    public static String translateToBinary(ArrayList<String> instructionParts) {
        String binaryInstruction = "";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(0)) + " ";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(1)) + " ";
        if (instructionParts.size() == 3) {
            binaryInstruction += numberToBinary(Integer.parseInt(instructionParts.get(2)));
        } else {
            binaryInstruction += "0".repeat(SystemConfig.REGISTER_VALUE_SIZE);
        }
        return binaryInstruction.trim();
    }
}
