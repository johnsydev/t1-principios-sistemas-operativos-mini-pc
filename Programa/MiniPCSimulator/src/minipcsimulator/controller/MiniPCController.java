/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipcsimulator.controller;

import minipcsimulator.gui.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import minipcsimulator.services.FileManager;
import minipcsimulator.services.AsmParser;
import minipcsimulator.services.BinaryUtils;

import minipcsimulator.model.Instruction;
import minipcsimulator.model.Loader;
import minipcsimulator.model.MainMemory;
import minipcsimulator.model.Process;

/**
 *
 * @author johns
 */
public class MiniPCController {
    //private MiniPCModel modelo;
    private VentanaPrincipal vista;
    private MainMemory memory;
    private Process process;

    public MiniPCController() {
        this.vista = new VentanaPrincipal();

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

        System.out.println("Archivo cargado y verificado correctamente.");
        System.out.println(asmArray);

        // Aquí se hace el proceso y las instrucciones se cargan, sin RAM aún
        this.process = new Process();
        List<Object[]> loadedProgramInstructions = Loader.loadProgram(lines, asmArray, this.process);

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

        // Aquí se carga el programa en la memoria principal (RAM)
        this.memory = new MainMemory();
        Loader.loadToMemory(this.process, this.memory);

        List<Object[]> memoryRows = this.memory.getAllMemoryRows();
        vista.actualizarTablaMemoria(memoryRows);
    }

    /**
     * Esto ejecuta el programa paso a paso, actualizando la vista de la memoria y el estado del proceso.
     * Estado del proceso RUNNING
     */
    private void ejecutarPasoAPaso() {
        this.process.setState(Process.ProcessState.RUNNING);

        
        
        vista.actualizarTablaMemoria(this.memory.getAllMemoryRows());
        vista.setEstadoBCP(this.process.getState().toString());
    }
}
