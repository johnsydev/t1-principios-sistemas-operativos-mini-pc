/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipcsimulator.controller;

import minipcsimulator.gui.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import minipcsimulator.services.FileManager;
import minipcsimulator.services.AsmParser;

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
                ArrayList<String> lines = new ArrayList<>();
                try {
                    lines = FileManager.loadFile();
                } catch (Exception exc) {
                    vista.mostrarError(exc.getMessage());
                    return;
                }
                
                boolean asmValid;
                
                try {
                    asmValid = AsmParser.verifySyntax(lines);
                } catch (Exception exc) {
                    vista.mostrarError(exc.getMessage());
                    return;
                }
                if (!asmValid) {
                    vista.mostrarError("El archivo ASM no es válido.");
                    return;
                }
                
                System.out.println("Archivo cargado y verificado correctamente.");
                System.out.println(lines);
            }
        });
    }
    
    public void actualizarVista() {
        
    }
}
