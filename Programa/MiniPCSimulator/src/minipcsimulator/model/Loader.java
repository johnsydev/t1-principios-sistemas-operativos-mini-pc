package minipcsimulator.model;

import java.util.ArrayList;
import java.util.List;
import minipcsimulator.services.BinaryUtils;
import minipcsimulator.utils.SystemConstants;

public class Loader {

    public static List<Object[]> loadProgram(ArrayList<String> lines, ArrayList<ArrayList<String>> asmArray, Process process) {
        int position = SystemConstants.USER_MEMORY_START_DEFAULT;
        int i = 0;
        List<Object[]> loadedProgramInstructions = new ArrayList<>();
        ArrayList<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            Instruction instruction = new Instruction(line, asmArray.get(i), BinaryUtils.translateToBinary(asmArray.get(i)));
            instructions.add(instruction);
            instruction.printConversion();

            loadedProgramInstructions.add(new Object[] {instruction.getOriginalInstructionText(), instruction.getBinaryInstruction()});
            i++;
        }
        process.setInstructions(instructions);
        return loadedProgramInstructions;
    }

    public static void loadToMemory(Process process, MainMemory memory) {
        ArrayList<Instruction> instructions = process.getInstructions();
        int position = SystemConstants.USER_MEMORY_START_DEFAULT;
        for (Instruction instruction : instructions) {
            memory.setPositionInstruction(position, instruction);
            position++;
        }
        process.getPCB().setState(PCB.ProcessState.READY);
    }
}
