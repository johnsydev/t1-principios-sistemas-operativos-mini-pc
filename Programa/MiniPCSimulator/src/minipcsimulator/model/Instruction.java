package minipcsimulator.model;

import java.util.ArrayList;

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

        ArrayList<String> parts = originalInstructionParts;
        this.instructionType = parts.get(0);
        this.register = parts.get(1);
        this.value = parts.get(2);
    }

    public void printConversion() {
        System.out.print(originalInstructionText);
        System.out.print("   ->   ");
        System.out.println(binaryInstruction);
    }
}
