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

/**
 *
 * @author johns
 */
public class MiniPCController {
    //private MiniPCModel modelo;
    private VentanaPrincipal vista;

    public MiniPCController() {
        this.vista = new VentanaPrincipal();
        
        actualizarVista();
        agregarListeners();

        // Muestra GUI
        this.vista.setVisible(true);
    }
    
    public void agregarListeners() {
        vista.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });
    }
    
    public void actualizarVista() {
        
    }

    private void cargarArchivo() {
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

        MainMemory memory = new MainMemory();
        Loader loader = new Loader(memory);
        List<Object[]> loadedProgramInstructions = loader.loadProgram(lines, asmArray);

        vista.actualizarTablaInstrucciones(loadedProgramInstructions);
        vista.actualizarTablaMemoria(memory.getAllMemoryRows());
    }
}
