package minipcsimulator.model;

import java.util.ArrayList;

/**
 * Clase que representa un proceso en el sistema operativo simulado.
 * Contiene la lista de instrucciones del proceso y su PCB (Process Control Block).
 */
public class Process {

    private ArrayList<Instruction> instructions;
    private PCB pcb;

    // al iniciar el proceso

    /**
     * Constructor de la clase Process.
     * Crea el proceso y su PCB.
     * @param id El identificador del proceso a crear, es un int simple que inicia en 1 que lo asigna el Kernel.
     * @param startPosition La posición de memoria donde inicia el proceso en la memoria principal (RAM).
     */
    public Process(int id, int startPosition) {
        this.instructions = new ArrayList<>(); //vacía esperando a Loader
        this.pcb = new PCB(id, startPosition);
    }

    /**
     * Asigna las instrucciones al proceso y configura la posición final del proceso en memoria.
     * Lo establece el Loader cuando carga el programa a disco.
     * @param instructions ArrayList de instrucciones que se asignarán al proceso.
     */
    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        this.pcb.configEndPosition(instructions.size());
    }

    /**
     * Obtiene el ArrayList de instrucciones del proceso.
     * @return El ArrayList de instrucciones del proceso.
     */
    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    /**
     * Obtiene el PCB del proceso.
     * @return El PCB del proceso.
     */
    public PCB getPCB() {
        return pcb;
    }
}
