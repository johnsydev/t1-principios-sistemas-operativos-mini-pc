package minipcsimulator.model;

import java.util.ArrayList;

public class Process {

    private ArrayList<Instruction> instructions;
    private PCB pcb;

    // al iniciar el proceso
    public Process(int id, int startPosition) {
        this.instructions = new ArrayList<>(); //vacía esperando a Loader
        this.pcb = new PCB(id, startPosition);
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        this.pcb.configEndPosition(instructions.size());
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public PCB getPCB() {
        return pcb;
    }
}
