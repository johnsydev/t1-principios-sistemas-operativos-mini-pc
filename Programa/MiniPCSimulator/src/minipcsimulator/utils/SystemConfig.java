package minipcsimulator.utils;

public class SystemConfig {

    // VALORES POR DEFECTO
    private static int memorySize = 256;
    private static int userMemoryStart = 64;

    //RAM
    public static final int MEMORY_SIZE_MIN = 128; //minimo según enunciado
    public static final int MEMORY_SIZE_MAX = 65536; //maximo (64 KB)

    // Separación con Kernel
    public static final int KERNEL_MEMORY_START = 0;
    public static final int USER_MEMORY_START_MIN = 16; // lo más mínimo
    
    //Para instrucciones y registros
    public static final int REGISTER_BIT_SIZE = 4; // MOV, ADD, ...
    public static final int INSTRUCTION_BIT_SIZE = 4; // AX, BX, ...
    public static final int REGISTER_VALUE_SIZE = 8; // números
    public static final int REGISTERS_COUNT = 4;

    public static int getMemorySize() {
        return memorySize;
    }

    public static int getUserMemoryStart() {
        return userMemoryStart;
    }

    public static void setMemorySize(int memorySize) {
        if (memorySize < MEMORY_SIZE_MIN || memorySize > MEMORY_SIZE_MAX) {
            throw new IllegalArgumentException("El tamaño de memoria debe estar entre " + MEMORY_SIZE_MIN + " y " + MEMORY_SIZE_MAX);
        }
        SystemConfig.memorySize = memorySize;
    }

    public static void setUserMemoryStart(int userMemoryStart) {
        if (userMemoryStart < USER_MEMORY_START_MIN || userMemoryStart >= memorySize) {
            throw new IllegalArgumentException("El inicio de memoria de usuario debe estar entre " + USER_MEMORY_START_MIN + " y " + (memorySize - 1));
        }
        SystemConfig.userMemoryStart = userMemoryStart;
    }
}
