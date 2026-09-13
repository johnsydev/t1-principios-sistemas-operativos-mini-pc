package minipcsimulator.model;

import minipcsimulator.services.BinaryUtils;
import minipcsimulator.services.BinaryUtils.BinaryCodes;

public class CPU {

    private MainMemory memory;
    private PCB pcb;

    private int PC = 0;
    private int AC = 0;
    private Instruction IR;
 
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
        if (PC >= pcb.getStartPosition() && PC < pcb.getEndPosition()) {
            this.IR = memory.getInstruction(PC);
            this.PC++;
        } else {
            // de momento el profe no puso instrucción de END, entonces se asume
            // que si se sale del rango de memoria del proceso, es porque terminó
            pcb.setState(PCB.ProcessState.EXIT);
            System.out.println("Proceso " + pcb.getPID() + " ha terminado.");
        }
    }

    public void execute() {
        String binary_opcode = IR.getInstructionType();
        String binary_register = IR.getRegister();
        String binary_value = IR.getValue();

        BinaryCodes opcode = BinaryCodes.getByBinaryCode(binary_opcode, "INSTRUCTION");
        BinaryCodes register = BinaryCodes.getByBinaryCode(binary_register, "REGISTER");
        int value = BinaryUtils.binaryToNumber(binary_value);

        if (opcode == null || register == null) {
            System.out.println("Error: Instrucción o registro no reconocido.");
            return;
        }

        switch (opcode) {
            case LOAD:
                executeLOAD(register);
                break;
            case STORE:
                executeSTORE(register);
                break;
            case ADD:
                executeADD(register);
                break;
            case SUB:
                executeSUB(register);
                break;
            case MOV:
                executeMOV(register, value);
                break;

            default:
                System.out.println("Error: Operación no reconocida.");
        }
        
    }


    public void executeLOAD(BinaryCodes register) {
        switch (register) {
            case AX:
                AC = AX;
                break;
            case BX:
                AC = BX;
                break;
            case CX:
                AC = CX;
                break;
            case DX:
                AC = DX;
                break;
            default:
                System.out.println("Error: Registro no reconocido.");
        }
    }

    public void executeSTORE(BinaryCodes register) {
        switch (register) {
            case AX:
                AX = AC;
                break;
            case BX:
                BX = AC;
                break;
            case CX:
                CX = AC;
                break;
            case DX:
                DX = AC;
                break;
            default:
                System.out.println("Error: Registro no reconocido.");
        }
    }

    public void executeADD(BinaryCodes register) {
        switch (register) {
            case AX:
                AC += AX;
                break;
            case BX:
                AC += BX;
                break;
            case CX:
                AC += CX;
                break;
            case DX:
                AC += DX;
                break;
            default:
                System.out.println("Error: Registro no reconocido.");
        }
    }

    public void executeSUB(BinaryCodes register) {
        switch (register) {
            case AX:
                AC -= AX;
                break;
            case BX:
                AC -= BX;
                break;
            case CX:
                AC -= CX;
                break;
            case DX:
                AC -= DX;
                break;
            default:
                System.out.println("Error: Registro no reconocido.");
        }
    }

    public void executeMOV(BinaryCodes register, int value) {
        switch (register) {
            case AX:
                AX = value;
                break;
            case BX:
                BX = value;
                break;
            case CX:
                CX = value;
                break;
            case DX:
                DX = value;
                break;
            default:
                System.out.println("Error: Registro no reconocido.");
        }
    }


    public void executeInstruction() {
        fetch();
        execute();
    }


    // GETTERS
    public int getPC() {
        return PC;
    }

    public String getIR() {
        return IR.getBinaryInstruction();
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
