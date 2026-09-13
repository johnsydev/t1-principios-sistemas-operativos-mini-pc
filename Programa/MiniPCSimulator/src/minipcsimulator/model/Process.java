package minipcsimulator.model;

import java.util.ArrayList;

public class Process {

    private ArrayList<Instruction> instructions;
    private PCB pcb;

    // al iniciar el proceso
    public Process(int id) {
        this.instructions = new ArrayList<>(); //vacía esperando a Loader
        this.pcb = new PCB(id);
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public PCB getPCB() {
        return pcb;
    }
}
