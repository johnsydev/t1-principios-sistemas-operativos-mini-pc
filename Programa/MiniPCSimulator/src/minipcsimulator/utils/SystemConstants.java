package minipcsimulator.utils;

public enum SystemConstants {
    ;
    public static final int MEMORY_SIZE = 256; //maximo definido
    public static final int KERNEL_MEMORY_START_DEFAULT = 0;
    public static final int USER_MEMORY_START_DEFAULT = 64; //de posicion 64 a 255 es memoria de usuario
    public static final int REGISTER_BIT_SIZE = 4; // MOV, ADD, ...
    public static final int INSTRUCTION_BIT_SIZE = 4; // AX, BX, ...
    public static final int REGISTER_VALUE_SIZE = 8; // números
    public static final int REGISTERS_COUNT = 4;
}
