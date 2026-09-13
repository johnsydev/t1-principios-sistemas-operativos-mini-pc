package minipcsimulator.model;

import minipcsimulator.utils.SystemConstants;

public class PCB {
    public enum ProcessState {
        NEW, //al seleccionar el archivo
        READY, //al cargar el programa a memoria
        RUNNING, //al ejecutarlo
        BLOCKED, //esperando IO
        EXIT //al terminar de ejecutarse
    }

    private int memoryPosition;
    private int startPosition;
    private int endPosition;
    
    
    private int PID = 0;
    private ProcessState state = ProcessState.NEW;
    private int PC = 0; // Program Counter
    private int AC = 0; // Acumulador

    // Registros
    private int AX = 0;
    private int BX = 0;
    private int CX = 0;
    private int DX = 0;
    
    // al iniciar el proceso
    public PCB(int id, int startPosition) {
        this.PID = 100+id;                                                       // 4 es el tamaño fijo de PCB + cantidad de registros
        this.startPosition = startPosition;
        this.memoryPosition = (SystemConstants.KERNEL_MEMORY_START_DEFAULT + (id-1)) * (4 + SystemConstants.REGISTERS_COUNT);
        this.state = ProcessState.NEW;
        this.PC = startPosition;
    }

    public void configEndPosition(int instructionsCount) {
        this.endPosition = startPosition + instructionsCount;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getPID() {
        return PID;
    }

    public ProcessState getState() {
        return state;
    }

    public int getMemoryPosition() {
        return memoryPosition;
    }

    /**
     * Cambia el estado del proceso.
     * @param state El nuevo estado del proceso.
     */
    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }    

    public int getAC() {
        return AC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public int getAX() {
        return AX;
    }

    public void setAX(int AX) {
        this.AX = AX;
    }

    public int getBX() {
        return BX;
    }

    public void setBX(int BX) {
        this.BX = BX;
    }

    public int getCX() {
        return CX;
    }

    public void setCX(int CX) {
        this.CX = CX;
    }

    public int getDX() {
        return DX;
    }

    public void setDX(int DX) {
        this.DX = DX;
    }
}
