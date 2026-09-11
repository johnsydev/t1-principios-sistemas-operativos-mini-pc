package minipcsimulator.services;

import java.util.ArrayList;

public class BinaryUtils {
    public enum BinaryCodes {
        LOAD("0001", 1), 
        STORE("0010", 1), 
        ADD("0101", 1), 
        SUB("0100", 1), 
        MOV("0011", 1),

        AX("0001", 0),
        BX("0010", 0),
        CX("0011", 0),
        DX("0100", 0);

        private final String binaryCode;
        private final int id;

        BinaryCodes(String binaryCode, int id) {
            this.binaryCode = binaryCode;
            this.id = id;
        }

        public String getBinaryCode() {
            return binaryCode;
        }

        // Con esto se puede acceder al código binario de las instrucciones y registros
        public static String getValueOf(String name) {
            try {
                return BinaryCodes.valueOf(name.toUpperCase()).getBinaryCode();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static String numberToBinary(int number) {
        char bitSigno = '0';
        if (number < 0) {
            bitSigno = '1'; // si es negativo
            number = -number;
        }

        String binarioValor = Integer.toBinaryString(number);
        binarioValor = String.format("%7s", binarioValor).replace(' ', '0'); // rellenar

        // recortar num si es muy grande, solo se toman los 7 bits menos significativos
        if (binarioValor.length() > 7) {
            binarioValor = binarioValor.substring(binarioValor.length() - 7);
        }

        return bitSigno + binarioValor;
    }

    public static String translateToBinary(ArrayList<String> instructionParts) {
        String binaryInstruction = "";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(0)) + " ";
        binaryInstruction += BinaryCodes.getValueOf(instructionParts.get(1)) + " ";
        if (instructionParts.size() == 3) {
            binaryInstruction += numberToBinary(Integer.parseInt(instructionParts.get(2)));
        } else {
            binaryInstruction += "00000000";
        }
        return binaryInstruction.trim();
    }
}
