package minipcsimulator.model;

public class CPU {

    private MainMemory memory;
    private PCB pcb;

    private int PC = 0;
    private int AC = 0;
    private int IR = 0;
 
    private int AX = 0;
    private int BX = 0;
    private int CX = 0;
    private int DX = 0;

    public CPU(MainMemory memory) {
        this.memory = memory;
    }

    
    public void setPCB(PCB pcb) {
        this.pcb = pcb;

        this.PC = pcb.getPC();
        this.AC = pcb.getAC();
        this.AX = pcb.getAX();
        this.BX = pcb.getBX();
        this.CX = pcb.getCX();
        this.DX = pcb.getDX();
    }


    public void fetch() {
        
    }

    public void decode() {
        // logica
    }

    public void execute() {
        // logica

    }


    public void executeInstruction() {
        fetch();
        decode();
        execute();
    }


    // GETTERS
    public int getPC() {
        return PC;
    }

    public int getAC() {
        return AC;
    }

    public int getAX() {
        return AX;
    }

    public int getBX() {
        return BX;
    }

    public int getCX() {
        return CX;
    }

    public int getDX() {
        return DX;
    }

}
