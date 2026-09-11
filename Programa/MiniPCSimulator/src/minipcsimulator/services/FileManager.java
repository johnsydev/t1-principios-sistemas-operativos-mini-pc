package minipcsimulator.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class FileManager {
    public static ArrayList<String> loadFile() {
        return openFileExplorer();
    }

    private static ArrayList<String> openFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            if (!isValidExtension(file.getAbsolutePath())) {
                throw new RuntimeException("El archivo seleccionado no tiene la extensión .asm.");
            }
            return readFileLines(file);
        } else {
            throw new RuntimeException("No se seleccionó ningún archivo.");
        }
    }

    public static boolean isValidExtension(String filePath) {
        return filePath.endsWith(".asm");
    }

    private static ArrayList<String> readFileLines(java.io.File file) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
