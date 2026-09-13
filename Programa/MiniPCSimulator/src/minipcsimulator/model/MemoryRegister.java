package minipcsimulator.model;

import minipcsimulator.services.BinaryUtils;

public class MemoryRegister {
    public String name;
    public int value;
    public String binaryValue;
    public Instruction instruction;

    public MemoryRegister(String name, int value) {
        this.name = name;
        this.value = value;
        this.binaryValue = BinaryUtils.numberToBinary(value);
    }

    public MemoryRegister(Instruction instruction) {
        this.instruction = instruction;
    }

    public String getBinaryValue() {
        return this.binaryValue;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
