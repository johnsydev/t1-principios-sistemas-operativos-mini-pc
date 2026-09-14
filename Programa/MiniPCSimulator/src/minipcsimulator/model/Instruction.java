package minipcsimulator.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que representa una instrucción en el sistema (traducida desde el .asm).
 * @author johnsydev
 */
public class Instruction {

    private String originalInstructionText;
    private ArrayList<String> instructionParts;
    private String binaryInstruction;

    // En binario:
    private String instructionType;
    private String register;
    private String value;

    /**
     * Constructor de la clase Instruction.
     * @param originalInstructionText El texto de la instrucción original.
     * @param originalInstructionParts ArrayList de partes de la instrucción original.
     * @param binaryInstruction La instrucción traducida a binario.
     */
    public Instruction(String originalInstructionText, ArrayList<String> originalInstructionParts, String binaryInstruction) {
        this.originalInstructionText = originalInstructionText.trim();
        this.instructionParts = originalInstructionParts;
        this.binaryInstruction = binaryInstruction;

        // Se cargan los datos de la instrucción en binario a sus atributos correspondientes
        ArrayList<String> partsBinary;
        if (binaryInstruction != null && !binaryInstruction.isEmpty()) {
            // Binario separado por espacios
            partsBinary = new ArrayList<>(Arrays.asList(binaryInstruction.trim().split(" ")));
            this.instructionType = partsBinary.get(0);
            this.register = partsBinary.get(1);
            this.value = partsBinary.get(2);
        }
    }

    /**
     * Imprime la conversión de la instrucción en consola.
     */
    public void printConversion() {
        System.out.print(originalInstructionText);
        System.out.print("   ->   ");
        System.out.println(binaryInstruction);
    }

    /**
     * Obtiene el texto original de la instrucción.
     * @return El texto original de la instrucción.
     */
    public String getOriginalInstructionText() {
        return originalInstructionText;
    }

    /**
     * Obtiene la instrucción traducida a binario.
     * @return La instrucción en binario.
     */
    public String getBinaryInstruction() {
        return binaryInstruction;
    }

    // Getters (En binario)

    /**
     * Obtiene el tipo de la instrucción en binario.
     * @return El tipo de la instrucción.
     */
    public String getInstructionType() {
        return instructionType;
    }

    /**
     * Obtiene el registro de la instrucción en binario.
     * @return El registro de la instrucción.
     */
    public String getRegister() {
        return register;
    }

    /**
     * Obtiene el valor de la instrucción en binario.
     * @return El valor de la instrucción.
     */
    public String getValue() {
        return value;
    }
}
