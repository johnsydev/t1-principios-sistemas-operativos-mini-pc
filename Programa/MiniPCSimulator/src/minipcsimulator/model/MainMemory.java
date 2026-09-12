package minipcsimulator.model;

import java.util.ArrayList;
import java.util.List;
import minipcsimulator.utils.SystemConstants;

public class MainMemory {
    
    private Instruction[] memory;
    
    /**
     * Constructor de la clase MainMemory.
     * Inicia la memoria principal con un arreglo de instrucciones vacías con el tamaño especificado.
     */
    public MainMemory() { 
        memory = new Instruction[SystemConstants.MEMORY_SIZE];
    }

    /**
     * Función para almacenar una instrucción en la memoria principal.
     * @param address La posición de memoria donde se almacenará la instrucción.
     * @param instruction El objeto Instruction que se almacenará en la memoria.
     */
    public void setPosition(int address, Instruction instruction) {
        memory[address] = instruction;
    }

    /**
     * Función para recuperar una instrucción de la memoria principal.
     * @param address La posición de memoria desde donde se recuperará la instrucción.
     * @return El objeto Instruction almacenado en la posición de memoria especificada.
     */
    public Instruction getPosition(int address) {
        return memory[address];
    }

    public List<Object[]> getAllMemoryRows() {
        List<Object[]> memoryList = new ArrayList<>();
        int i = 0;
        for (Instruction instruction : memory) {
            if (instruction != null) {
                memoryList.add(new Object[] {i, instruction.getOriginalInstructionText(), instruction.getBinaryInstruction()});
            } else {
                memoryList.add(new Object[] {i, null, null});
            }
            i++;
        }
        return memoryList;
    }
}
