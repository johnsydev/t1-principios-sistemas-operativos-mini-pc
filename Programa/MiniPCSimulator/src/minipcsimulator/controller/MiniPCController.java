/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipcsimulator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import minipcsimulator.gui.VentanaPrincipal;
import minipcsimulator.model.CPU;
import minipcsimulator.model.Dispatcher;
import minipcsimulator.model.Loader;
import minipcsimulator.model.MainMemory;
import minipcsimulator.model.MemoryRegister;
import minipcsimulator.model.PCB;
import minipcsimulator.model.PCB.ProcessState;
import minipcsimulator.model.Process;
import minipcsimulator.services.AsmParser;
import minipcsimulator.services.FileManager;
import minipcsimulator.utils.SystemConfig;

/**
 *
 * @author johns
 */
public class MiniPCController {
    //private MiniPCModel modelo;
    private VentanaPrincipal vista;
    private MainMemory memory;
    private Process process;
    private CPU cpu;
    private ArrayList<Integer> listProcessMemory = new ArrayList<>();
    // lista de procesos: [1, 0, 3, ...], los procesos en 0 terminaron y se pueden reemplazar
    // los que tienen número ese es su ID

    public MiniPCController() {
        this.vista = new VentanaPrincipal();
        this.memory = new MainMemory();
        this.cpu = new CPU(this.memory);

        actualizarVista();
        agregarListeners();

        // Muestra GUI
        this.vista.setVisible(true);
    }
    
    public void agregarListeners() {
        vista.getBtnSeleccionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarArchivo();
            }
        });

        vista.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPrograma();
            }
        });

        vista.getBtnPasoAPaso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarPasoAPaso();
            }
        });

        vista.getBtnEjecutar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarTodoPrograma();
            }
        });

        vista.getBtnAplicarConfig().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarConfiguraciones();
            }
        });

        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarSistema();
            }
        });
    }
    
    public void actualizarVista() {
        
    }

    /**
     * Esto abre el cuadro de diálogo para seleccionar un archivo .asm, lo carga y verifica su sintaxis.
     * Genera el proceso (estado NEW)
     * Genera la tabla de instrucciones en la GUI
     */
    private void seleccionarArchivo() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines = FileManager.loadFile();
        } catch (Exception exc) {
            vista.mostrarError(exc.getMessage());
            return;
        }
        
        ArrayList<ArrayList<String>> asmArray = new ArrayList<>();
        
        try {
            asmArray = AsmParser.verifySyntax(lines);
        } catch (Exception exc) {
            vista.mostrarError(exc.getMessage());
            return;
        }

        if (asmArray.isEmpty()) {
            vista.mostrarError("El archivo está vacío o no contiene instrucciones válidas.");
            return;
        }

        if (asmArray.size() > SystemConfig.getUserMemorySize()) {
            vista.mostrarError("El programa ingresado es demasiado grande para la memoria disponible. Tamaño máximo permitido: " + SystemConfig.getUserMemorySize() + " instrucciones.");
            return;
        }

        System.out.println("Archivo cargado y verificado correctamente.");
        System.out.println(asmArray);

        // Aquí se hace el proceso y las instrucciones se cargan, sin RAM aún

        int zeroIndex = listProcessMemory.indexOf(0); 
        int assignedId;

        if (zeroIndex == -1) { // si no hay procesos terminados, se asigna un nuevo ID
            assignedId = listProcessMemory.size() + 1; 
            listProcessMemory.add(assignedId);
        } else {
            assignedId = zeroIndex + 1;
            listProcessMemory.set(zeroIndex, assignedId); // si hay procesos terminados, se reemplaza el primero
        }

        this.process = new Process(assignedId, SystemConfig.getUserMemoryStart());
        List<Object[]> loadedProgramInstructions = Loader.loadProgram(lines, asmArray, this.process);

        vista.deshabilitarConfiguraciones();
        vista.actualizarTablaInstrucciones(loadedProgramInstructions);
    }

    /**
     * Esto carga el programa en la memoria principal (RAM) y actualiza la vista de la memoria.
     * Estado del proceso READY
     */
    private void cargarPrograma() {
        if (this.process == null) {
            vista.mostrarError("No hay un programa cargado. Seleccione un archivo .asm primero.");
            return;
        }

        Loader.loadToMemory(this.process, this.memory);

        List<Object[]> memoryRows = this.memory.getAllMemoryRows();
        vista.actualizarTablaMemoria(memoryRows);
    }

    private boolean validarParaEjecutar() {
        if (this.process == null) {
            vista.mostrarError("No hay un programa cargado. Seleccione un archivo .asm primero.");
            return false;
        }
        else if (this.process.getPCB().getState() == ProcessState.EXIT) {
            vista.mostrarError("El proceso ya ha terminado. Seleccione un nuevo archivo .asm para cargar otro programa.");
            return false;
        }
        else if (this.process.getPCB().getState() == ProcessState.NEW) {
            vista.mostrarError("El proceso aún no ha sido cargado en memoria. Cargue el programa primero.");
            return false;
        }
        else if (this.process.getPCB().getState() == ProcessState.BLOCKED) {
            vista.mostrarError("El proceso está bloqueado. No se puede ejecutar hasta que se desbloquee.");
            return false;
        }
        return true;
    }

    /**
     * Esto ejecuta el programa paso a paso, actualizando la vista de la memoria y el estado del proceso.
     * Estado del proceso RUNNING
     */
    private void ejecutarPasoAPaso() {
        if (!validarParaEjecutar()) {
            return;
        }

        // Si el proceso es válido, vamos a ejecutarlo.

        // Para ejecutar primer paso se debe llamar al dispatcher
        if (this.process.getPCB().getState() == ProcessState.READY) {
            Dispatcher.dispatch(this.process, this.cpu);
        }

        // Ejecutar la instrucción actual
        this.cpu.executeInstruction();

        saveRegistersIntoMemory();
        List<Object[]> memoryRows = this.memory.getAllMemoryRows();
        vista.actualizarTablaMemoria(memoryRows);
    }

    private void ejecutarTodoPrograma() {
        if (!validarParaEjecutar()) {
            return;
        }
        while (this.process.getPCB().getState() != ProcessState.EXIT) {
            ejecutarPasoAPaso();
        }
    }

    private void saveRegistersIntoMemory() {
        PCB pcb = this.process.getPCB();
        pcb.setPC(this.cpu.getPC());
        pcb.setAC(this.cpu.getAC());
        pcb.setAX(this.cpu.getAX());
        pcb.setBX(this.cpu.getBX());
        pcb.setCX(this.cpu.getCX());
        pcb.setDX(this.cpu.getDX());
        
        // pos memoria BCP
        int memoryPosition = pcb.getMemoryPosition();
        System.out.println("Guardando registros en memoria en la posición: " + memoryPosition);
        memory.setPosition(memoryPosition, new MemoryRegister("bcp_pid", pcb.getPID()));
        memory.setPosition(memoryPosition+1, new MemoryRegister("bcp_state", pcb.getState().ordinal()));
        memory.setPosition(memoryPosition+2, new MemoryRegister("bcp_pc", pcb.getPC()));
        memory.setPosition(memoryPosition+3, new MemoryRegister("bcp_ac", pcb.getAC()));
        memory.setPosition(memoryPosition+4, new MemoryRegister("bcp_ax", pcb.getAX()));
        memory.setPosition(memoryPosition+5, new MemoryRegister("bcp_bx", pcb.getBX()));
        memory.setPosition(memoryPosition+6, new MemoryRegister("bcp_cx", pcb.getCX()));
        memory.setPosition(memoryPosition+7, new MemoryRegister("bcp_dx", pcb.getDX()));

        vista.setPC(pcb.getPC());
        vista.setIR(cpu.getIR());
        vista.setAC(pcb.getAC());
        vista.setAX(pcb.getAX());
        vista.setBX(pcb.getBX());
        vista.setCX(pcb.getCX());
        vista.setDX(pcb.getDX());
    }



    // Utils

    private void aplicarConfiguraciones() {
        int memorySize = vista.getTamanoMemoriaSeleccionado();
        int kernelSize = vista.getLimiteKernelSeleccionado();

        if (memorySize <= 0 || kernelSize < 0 || kernelSize >= memorySize || kernelSize < SystemConfig.USER_MEMORY_START_MIN 
            || memorySize > SystemConfig.MEMORY_SIZE_MAX || memorySize < SystemConfig.MEMORY_SIZE_MIN || kernelSize > SystemConfig.MEMORY_SIZE_MAX-16) {
            vista.mostrarError("Rango de memoria inválido. Asegúrese de que el inicio sea menor que el fin y ambos estén dentro del rango permitido.");
            return;
        }

        SystemConfig.setMemorySize(memorySize);
        SystemConfig.setUserMemoryStart(kernelSize);

        this.memory = null; //sacamos memoria vieja
        this.cpu = null; //sacamos cpu vieja

        this.memory = new MainMemory(); // ponemos memoria nueva
        this.cpu = new CPU(this.memory); // ponemos cpu nueva

        System.out.println("Configuraciones aplicadas correctamente.");
    }

    private void reiniciarSistema() {
        this.memory = null; //sacamos memoria vieja
        this.cpu = null; //sacamos cpu vieja

        this.memory = new MainMemory(); // ponemos memoria nueva
        this.cpu = new CPU(this.memory); // ponemos cpu nueva

        this.process = null; // sacamos proceso viejo
        listProcessMemory.clear(); // limpiamos lista de procesos

        vista.limpiarVista();
        vista.habilitarConfiguraciones();
    }
}
