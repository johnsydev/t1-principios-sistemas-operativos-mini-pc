package minipcsimulator.model;

import minipcsimulator.services.BinaryUtils;
import minipcsimulator.utils.SystemConstants;

import java.util.ArrayList;
import java.util.List;


public class Loader {
    public MainMemory memory;

    public Loader(MainMemory memory) {
        this.memory = memory; 
    }

    public List<Object[]> loadProgram(ArrayList<String> lines, ArrayList<ArrayList<String>> asmArray) {
        int position = SystemConstants.USER_MEMORY_START_DEFAULT;
        int i = 0;
        List<Object[]> loadedProgramInstructions = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            Instruction instruction = new Instruction(line, asmArray.get(i), BinaryUtils.translateToBinary(asmArray.get(i)));
            instruction.printConversion();
            memory.setPosition(position, instruction);
            loadedProgramInstructions.add(new Object[] {instruction.getOriginalInstructionText(), instruction.getBinaryInstruction()});
            i++;
            position++;
        }
        return loadedProgramInstructions;
    }
}
