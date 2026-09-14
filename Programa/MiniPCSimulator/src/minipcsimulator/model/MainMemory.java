package minipcsimulator.model;

import java.util.ArrayList;
import java.util.List;
import minipcsimulator.utils.SystemConfig;

public class MainMemory {
    
    private MemoryRegister[] memory;
    
    /**
     * Constructor de la clase MainMemory.
     * Inicia la memoria principal con un arreglo de instrucciones vacías con el tamaño especificado.
     */
    public MainMemory() { 
        memory = new MemoryRegister[SystemConfig.getMemorySize()];
    }

    public void setPosition(int address, MemoryRegister register) {
        memory[address] = register;
    }

    /**
     * Función para almacenar una instrucción en la memoria principal.
     * @param address La posición de memoria donde se almacenará la instrucción.
     * @param instruction El objeto Instruction que se almacenará en la memoria.
     */
    public void setPositionInstruction(int address, Instruction instruction) {
        memory[address] = new MemoryRegister(instruction);
    }

    /**
     * Función para recuperar una instrucción de la memoria principal.
     * @param address La posición de memoria desde donde se recuperará la instrucción.
     * @return El objeto MemoryRegister almacenado en la posición de memoria especificada.
     */
    public MemoryRegister getPosition(int address) {
        return memory[address];
    }

    public Instruction getInstruction(int address) {
        MemoryRegister memReg = memory[address];
        if (memReg != null) {
            return memReg.instruction;
        }
        return null;
    }

    public List<Object[]> getAllMemoryRows() {
        List<Object[]> memoryList = new ArrayList<>();
        int i = 0;
        int countPauseKernelStart = -1;
        for (MemoryRegister memr : memory) {
            if (memr == null) {
                
                if (i < SystemConfig.getUserMemoryStart()) {
                    if (countPauseKernelStart == -1) {
                        countPauseKernelStart = i;
                    }
                }
                else {
                    if (countPauseKernelStart != -1) {
                        memoryList.add(new Object[] {countPauseKernelStart + " - " + (i - 1), "Kernel reservado", null});
                        countPauseKernelStart = -1;
                    }
                    memoryList.add(new Object[] {i, null, null});
                }
                i++;
                continue;
            }
            if (countPauseKernelStart != -1) {
                memoryList.add(new Object[] {countPauseKernelStart + "..." + (i - 1), "Kernel reservado", null});
                countPauseKernelStart = -1;
            }
            if (memr.instruction != null) {
                memoryList.add(new Object[] {i, memr.instruction.getOriginalInstructionText(), memr.instruction.getBinaryInstruction()});
            } else {
                memoryList.add(new Object[] {i, memr.getName(), memr.getBinaryValue()});
            }
            i++;
        }
        return memoryList;
    }
}
