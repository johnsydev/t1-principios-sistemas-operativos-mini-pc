/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package minipcsimulator;

import com.formdev.flatlaf.FlatDarkLaf;
import minipcsimulator.controller.MiniPCController;
import minipcsimulator.gui.VentanaPrincipal;

/**
 *
 * @author johns
 */
public class MiniPCSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("No se pudo cargar el tema FlatLaf, se usará el tema por defecto.");
        }

        // inicializa la app
        java.awt.EventQueue.invokeLater(() -> {
            new MiniPCController();
        });
    }
    
}
