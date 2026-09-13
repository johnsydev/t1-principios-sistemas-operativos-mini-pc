package minipcsimulator.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Instruction {

    private String originalInstructionText;
    private ArrayList<String> instructionParts;
    private String binaryInstruction;

    private String instructionType;
    private String register;
    private String value;

    public Instruction(String originalInstructionText, ArrayList<String> originalInstructionParts, String binaryInstruction) {
        this.originalInstructionText = originalInstructionText.trim();
        this.instructionParts = originalInstructionParts;
        this.binaryInstruction = binaryInstruction;

        ArrayList<String> partsBinary;
        if (binaryInstruction != null && !binaryInstruction.isEmpty()) {
            partsBinary = new ArrayList<>(Arrays.asList(binaryInstruction.trim().split(" ")));
            this.instructionType = partsBinary.get(0);
            this.register = partsBinary.get(1);
            this.value = partsBinary.get(2);
        }
    }

    public void printConversion() {
        System.out.print(originalInstructionText);
        System.out.print("   ->   ");
        System.out.println(binaryInstruction);
    }

    public String getOriginalInstructionText() {
        return originalInstructionText;
    }

    public String getBinaryInstruction() {
        return binaryInstruction;
    }

    // Getters (En binario)
    public String getInstructionType() {
        return instructionType;
    }

    public String getRegister() {
        return register;
    }

    public String getValue() {
        return value;
    }
}
