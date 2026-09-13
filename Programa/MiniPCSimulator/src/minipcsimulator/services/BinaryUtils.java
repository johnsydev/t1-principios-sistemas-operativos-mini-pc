package minipcsimulator.services;

import java.util.ArrayList;
import minipcsimulator.utils.SystemConstants;

public class BinaryUtils {
    public enum BinaryCodes {
        LOAD("0001", 1, "INSTRUCTION"), 
        STORE("0010", 2, "INSTRUCTION"), 
        ADD("0101", 3, "INSTRUCTION"), 
        SUB("0100", 4, "INSTRUCTION"), 
        MOV("0011", 5, "INSTRUCTION"),

        AX("0001", 5, "REGISTER"),
        BX("0010", 6, "REGISTER"),
        CX("0011", 7, "REGISTER"),
        DX("0100", 8, "REGISTER");

        private final String binaryCode;
        private final int id;
        private final String type;

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

        // Con esto se puede acceder al código binario de las instrucciones y registros
        public static String getValueOf(String name) {
            try {
                return BinaryCodes.valueOf(name.toUpperCase()).getBinaryCode();
            } catch (Exception e) {
                return null;
            }
        }

        public static BinaryCodes getByBinaryCode(String code, String type) {
            for (BinaryCodes codes : values()) {
                if (codes.binaryCode.equals(code) && codes.type.equals(type)) {
                    return codes;
                }
            }
            return null;
        }
    }

    public static String numberToBinary(int number) {
        char bitSigno = '0';
        if (number < 0) {
            bitSigno = '1'; // si es negativo
            number = -number;
        }

        String binarioValor = Integer.toBinaryString(number);
        binarioValor = String.format("%" + (SystemConstants.REGISTER_VALUE_SIZE - 1) + "s", binarioValor).replace(' ', '0'); // rellenar

        // recortar num si es muy grande, solo se toman los 7 bits menos significativos
        if (binarioValor.length() > SystemConstants.REGISTER_VALUE_SIZE - 1) {
            binarioValor = binarioValor.substring(binarioValor.length() - (SystemConstants.REGISTER_VALUE_SIZE - 1));
        }

        return bitSigno + binarioValor;
    }

    public static int binaryToNumber(String binary) {
        if (binary.length() != SystemConstants.REGISTER_VALUE_SIZE) {
            throw new IllegalArgumentException("El binario debe tener una longitud de " + SystemConstants.REGISTER_VALUE_SIZE);
        }

        char bitSign = binary.charAt(0);
        String binaryValue = binary.substring(1);

        int number = Integer.parseInt(binaryValue, 2);
        if (bitSign == '1') {
            number = -number; 
        }

        return number;
    }

    public static String translateToBinary(ArrayList<String> instructionParts) {
        String binaryInstruction = "";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(0)) + " ";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(1)) + " ";
        if (instructionParts.size() == 3) {
            binaryInstruction += numberToBinary(Integer.parseInt(instructionParts.get(2)));
        } else {
            binaryInstruction += "0".repeat(SystemConstants.REGISTER_VALUE_SIZE);
        }
        return binaryInstruction.trim();
    }
}
