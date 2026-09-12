package minipcsimulator.model;

import java.util.ArrayList;

public class Process {
    public enum ProcessState {
        NEW, //al seleccionar el archivo
        READY, //al cargar el programa a memoria
        RUNNING, //al ejecutarlo
        BLOCKED, //esperando IO
        EXIT //al terminar de ejecutarse
    }

    public ProcessState state;
    private int PID;
    private ArrayList<Instruction> instructions;

    // al iniciar el proceso
    public Process() {
        this.PID = 101; //de momento
        this.state = ProcessState.NEW;
        this.instructions = new ArrayList<>(); //vacía esperando a Loader
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public int getPID() {
        return PID;
    }

    public ProcessState getState() {
        return state;
    }

    /**
     * Cambia el estado del proceso.
     * @param state El nuevo estado del proceso.
     */
    public void setState(ProcessState state) {
        this.state = state;
    }
}
