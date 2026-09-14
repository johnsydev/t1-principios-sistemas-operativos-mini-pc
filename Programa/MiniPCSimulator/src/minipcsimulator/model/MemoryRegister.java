package minipcsimulator.model;

import minipcsimulator.services.BinaryUtils;

/**
 * Clase que representa un registro de memoria, que puede ser un registro de datos o una instrucción.
 * Fue creada pues almacenar solo instrucciones limitaba la capacidad de guardar datos en memoria RAM con otras finalidades.
 */
public class MemoryRegister {
    public String name;
    public int value;
    public String binaryValue;
    public Instruction instruction;

    /**
     * Constructor manual de la clase MemoryRegister para registros de datos.
     * Convierte el valor entero a binario y lo almacena junto con el nombre del registro.
     * @param name El nombre del registro de memoria.
     * @param value El valor entero del registro de memoria.
     */
    public MemoryRegister(String name, int value) {
        this.name = name;
        this.value = value;
        this.binaryValue = BinaryUtils.numberToBinary(value);
    }

    /**
     * Constructor de la clase MemoryRegister para registros de instrucciones (objeto Instruction).
     * @param instruction La instrucción que se almacenará en el registro de memoria.
     */
    public MemoryRegister(Instruction instruction) {
        this.instruction = instruction;
    }

    /**
     * Obtiene el valor binario del registro de memoria.
     * @return El valor binario del registro de memoria.
     */
    public String getBinaryValue() {
        return this.binaryValue;
    }

    /**
     * Obtiene el valor entero del registro de memoria.
     * @return El valor entero del registro de memoria.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Obtiene el nombre del registro de memoria.
     * @return El nombre del registro de memoria.
     */
    public String getName() {
        return this.name;
    }
}
