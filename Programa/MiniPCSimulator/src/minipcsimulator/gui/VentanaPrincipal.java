package minipcsimulator.gui;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // Paleta de colores
    private static final Color BG_MAIN = new Color(18, 20, 24);
    private static final Color BG_PANEL = new Color(25, 28, 34);
    private static final Color BG_CARD = new Color(31, 35, 42);
    private static final Color BG_INPUT = new Color(22, 25, 30);

    private static final Color BORDER = new Color(55, 61, 70);
    private static final Color TEXT = new Color(235, 238, 242);
    private static final Color TEXT_SECONDARY = new Color(155, 163, 174);

    private static final Color BLUE = new Color(45, 140, 240);
    private static final Color GREEN = new Color(45, 190, 115);
    private static final Color RED = new Color(220, 75, 85);
    private static final Color CYAN = new Color(65, 190, 205);
    private static final Color ORANGE = new Color(235, 160, 65);

    // Componentes de la interfaz
    private JButton btnSeleccionar, btnCargar, btnPasoAPaso, btnEjecutar, btnLimpiar;
    private JSpinner spTamanoMemoria, spLimiteKernel;
    private JButton btnAplicarConfig;
    private JTable tablaInstrucciones;
    private DefaultTableModel modeloTablaInstrucciones;
    private JTable tablaMemoria;
    private DefaultTableModel modeloTablaMemoria;
    private JTextField txtPC, txtIR, txtAC, txtAX, txtBX, txtCX, txtDX;
    private JLabel lblEstadoBCP, lblProcessID;

    public VentanaPrincipal() {
        initComponents();
    }

    // Inicializa la ventana principal
    private void initComponents() {
        setTitle("Mini PC Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 720);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout());

        JPanel contenedor = new JPanel(new BorderLayout(16, 16));
        contenedor.setBackground(BG_MAIN);
        contenedor.setBorder(new EmptyBorder(18, 18, 18, 18));

        contenedor.add(crearHeader(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(16, 0));
        contenido.setBackground(BG_MAIN);

        contenido.add(crearPanelControles(), BorderLayout.WEST);
        contenido.add(crearPanelMemoria(), BorderLayout.CENTER);
        contenido.add(crearPanelBCP(), BorderLayout.EAST);

        contenedor.add(contenido, BorderLayout.CENTER);
        add(contenedor, BorderLayout.CENTER);
    }

    // Crea el panel superior (Header)
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_MAIN);
        header.setPreferredSize(new Dimension(0, 58));

        JPanel tituloPanel = new JPanel();
        tituloPanel.setOpaque(false);
        tituloPanel.setLayout(new BoxLayout(tituloPanel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("MINI PC SIMULATOR");
        titulo.setForeground(TEXT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitulo = new JLabel("Principios de Sistemas Operativos");
        subtitulo.setForeground(TEXT_SECONDARY);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));

        tituloPanel.add(titulo);
        tituloPanel.add(Box.createVerticalStrut(2));
        tituloPanel.add(subtitulo);

        JPanel estadoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        estadoPanel.setOpaque(false);

        JLabel punto = new JLabel("●");
        punto.setForeground(GREEN);
        punto.setFont(new Font("SansSerif", Font.BOLD, 15));

        JLabel estado = new JLabel("SIMULADOR LISTO");
        estado.setForeground(TEXT_SECONDARY);
        estado.setFont(new Font("SansSerif", Font.BOLD, 12));

        estadoPanel.add(punto);
        estadoPanel.add(estado);

        header.add(tituloPanel, BorderLayout.WEST);
        header.add(estadoPanel, BorderLayout.EAST);

        return header;
    }

    // Crea el panel de controles izquierdo
    private JPanel crearPanelControles() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(BG_PANEL);
        panelPrincipal.setBorder(new EmptyBorder(16, 14, 16, 14));
        panelPrincipal.setPreferredSize(new Dimension(220, 0));

        JLabel titulo = crearTitulo("CONTROLES");
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(14));

        btnSeleccionar = crearBoton("Seleccionar archivo .asm", BLUE);
        btnCargar = crearBoton("Cargar programa", ORANGE);
        btnPasoAPaso = crearBoton("Paso a paso", CYAN);
        btnEjecutar = crearBoton("Ejecutar todo", GREEN);
        btnLimpiar = crearBoton("Limpiar / Reset", RED);

        panelPrincipal.add(btnSeleccionar);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(btnCargar);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(btnPasoAPaso);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(btnEjecutar);
        panelPrincipal.add(Box.createVerticalStrut(8));
        panelPrincipal.add(btnLimpiar);

        panelPrincipal.add(Box.createVerticalStrut(22));

        JSeparator separador = new JSeparator();
        separador.setForeground(BORDER);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        panelPrincipal.add(separador);
        panelPrincipal.add(Box.createVerticalStrut(18));

        JLabel tituloRAM = crearTitulo("MEMORIA RAM");
        panelPrincipal.add(tituloRAM);
        panelPrincipal.add(Box.createVerticalStrut(14));

        JPanel cardRAM = crearCard();
        cardRAM.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRAM = crearLabel("Tamaño RAM");
        JLabel lblKernel = crearLabel("Espacio Kernel");

        spTamanoMemoria = new JSpinner(new SpinnerNumberModel(256, 128, 1024, 16));
        spLimiteKernel = new JSpinner(new SpinnerNumberModel(64, 16, 256, 8));

        estilizarSpinner(spTamanoMemoria);
        estilizarSpinner(spLimiteKernel);

        btnAplicarConfig = new JButton("Aplicar configuración");
        estilizarBotonSecundario(btnAplicarConfig);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        cardRAM.add(lblRAM, gbc);

        gbc.gridy = 1;
        cardRAM.add(spTamanoMemoria, gbc);

        gbc.gridy = 2;
        cardRAM.add(lblKernel, gbc);

        gbc.gridy = 3;
        cardRAM.add(spLimiteKernel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(14, 4, 4, 4);
        cardRAM.add(btnAplicarConfig, gbc);

        panelPrincipal.add(cardRAM);

        return panelPrincipal;
    }

    // Crea el panel central con las tablas de programa y memoria
    private JPanel crearPanelMemoria() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 12, 0));
        panel.setBackground(BG_MAIN);

        JPanel cardPrograma = crearCard();
        cardPrograma.setLayout(new BorderLayout(0, 12));

        JPanel encabezadoPrograma = crearEncabezadoSeccion("PROGRAMA CARGADO", "ASM / BINARIO");
        cardPrograma.add(encabezadoPrograma, BorderLayout.NORTH);

        String[] colsInst = {"Instrucción ASM", "Código Binario"};
        modeloTablaInstrucciones = new DefaultTableModel(colsInst, 0);
        tablaInstrucciones = crearTabla(modeloTablaInstrucciones);

        JScrollPane scrollInstrucciones = new JScrollPane(tablaInstrucciones);
        scrollInstrucciones.setBorder(BorderFactory.createEmptyBorder());
        cardPrograma.add(scrollInstrucciones, BorderLayout.CENTER);

        JPanel cardRAM = crearCard();
        cardRAM.setLayout(new BorderLayout(0, 12));

        JPanel encabezadoRAM = crearEncabezadoSeccion("MEMORIA PRINCIPAL", "RAM");
        cardRAM.add(encabezadoRAM, BorderLayout.NORTH);

        String[] colsMem = {"Posición", "Instrucción", "Valor en Memoria"};
        modeloTablaMemoria = new DefaultTableModel(colsMem, 0);
        tablaMemoria = crearTabla(modeloTablaMemoria);

        JScrollPane scrollMemoria = new JScrollPane(tablaMemoria);
        scrollMemoria.setBorder(BorderFactory.createEmptyBorder());
        cardRAM.add(scrollMemoria, BorderLayout.CENTER);

        panel.add(cardPrograma);
        panel.add(cardRAM);

        return panel;
    }

    // Crea el panel derecho para BCP y registros del CPU
    private JPanel crearPanelBCP() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(BG_PANEL);
        panelPrincipal.setBorder(new EmptyBorder(16, 14, 16, 14));
        panelPrincipal.setPreferredSize(new Dimension(285, 0));

        JLabel titulo = crearTitulo("PROCESO");
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(14));

        JPanel cardProceso = crearCard();
        cardProceso.setLayout(new BorderLayout(8, 8));

        lblProcessID = new JLabel("PID 101");
        lblProcessID.setForeground(TEXT);
        lblProcessID.setFont(new Font("SansSerif", Font.BOLD, 17));

        lblEstadoBCP = new JLabel("ESTADO: ESPERANDO ARCHIVO");
        lblEstadoBCP.setForeground(ORANGE);
        lblEstadoBCP.setFont(new Font("SansSerif", Font.BOLD, 11));

        cardProceso.add(lblProcessID, BorderLayout.NORTH);
        cardProceso.add(lblEstadoBCP, BorderLayout.SOUTH);

        panelPrincipal.add(cardProceso);
        panelPrincipal.add(Box.createVerticalStrut(18));

        JLabel tituloCPU = crearTitulo("REGISTROS CPU");
        panelPrincipal.add(tituloCPU);
        panelPrincipal.add(Box.createVerticalStrut(12));

        JPanel registros = new JPanel(new GridLayout(4, 2, 8, 8));
        registros.setOpaque(false);

        txtPC = crearCampoRegistro();
        txtIR = crearCampoRegistro();
        txtAC = crearCampoRegistro();
        txtAX = crearCampoRegistro();
        txtBX = crearCampoRegistro();
        txtCX = crearCampoRegistro();
        txtDX = crearCampoRegistro();

        registros.add(crearRegistroCard("PC", txtPC));
        registros.add(crearRegistroCard("IR", txtIR));
        registros.add(crearRegistroCard("AC", txtAC));
        registros.add(crearRegistroCard("AX", txtAX));
        registros.add(crearRegistroCard("BX", txtBX));
        registros.add(crearRegistroCard("CX", txtCX));
        registros.add(crearRegistroCard("DX", txtDX));

        JPanel vacio = new JPanel();
        vacio.setOpaque(false);
        registros.add(vacio);

        panelPrincipal.add(registros);

        return panelPrincipal;
    }

    // Utilidad para crear tarjetas visuales contenedoras
    private JPanel crearCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        new EmptyBorder(14, 14, 14, 14)
                )
        );
        return card;
    }

    // Crea el encabezado para las secciones de las tablas
    private JPanel crearEncabezadoSeccion(String titulo, String descripcion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(TEXT);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setForeground(TEXT_SECONDARY);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 11));

        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(lblDescripcion, BorderLayout.EAST);

        return panel;
    }

    // Crea un botón principal estilizado
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(190, 38));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        boton.putClientProperty(
                FlatClientProperties.STYLE,
                "background: " + convertirColor(color) + "; " +
                "foreground: #FFFFFF; " +
                "font: bold;"
        );

        return boton;
    }

    // Estiliza un botón secundario
    private void estilizarBotonSecundario(JButton boton) {
        boton.setPreferredSize(new Dimension(190, 36));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        boton.setFocusPainted(false);

        boton.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #252B33; " +
                "foreground: #DDE2E8; " +
                "borderWidth: 1; " +
                "borderColor: #3B434E;"
        );
    }

    // Aplica estilos al JSpinner
    private void estilizarSpinner(JSpinner spinner) {
        spinner.setPreferredSize(new Dimension(190, 34));
        spinner.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #16191E; " +
                "foreground: #E8EBEF; " +
                "borderColor: #414954;"
        );
    }

    // Configura y crea una tabla personalizada
    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(BG_CARD);
        tabla.setForeground(TEXT);
        tabla.setSelectionBackground(new Color(45, 75, 105));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));

        tabla.getTableHeader().setPreferredSize(new Dimension(0, 34));
        tabla.getTableHeader().setBackground(new Color(37, 42, 50));
        tabla.getTableHeader().setForeground(TEXT_SECONDARY);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(BG_CARD);
                    } else {
                        c.setBackground(new Color(28, 32, 38));
                    }
                    c.setForeground(TEXT);
                }

                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        };

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        return tabla;
    }

    // Crea un campo de texto para los registros del CPU
    private JTextField crearCampoRegistro() {
        JTextField tf = new JTextField("0");
        tf.setEditable(false);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tf.setFont(new Font("Monospaced", Font.BOLD, 14));

        tf.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #161A20; " +
                "foreground: #4EC9B0; " +
                "caretColor: #4EC9B0;"
        );

        return tf;
    }

    // Crea el contenedor visual para un registro de CPU
    private JPanel crearRegistroCard(String nombre, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(BG_CARD);

        JLabel label = new JLabel(nombre);
        label.setForeground(TEXT_SECONDARY);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));

        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }

    // Crea etiquetas de texto secundarias
    private JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(TEXT_SECONDARY);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        return label;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(TEXT_SECONDARY);
        label.setFont(new Font("SansSerif", Font.PLAIN, 11));
        return label;
    }

    // Convierte un objeto Color a su representación hexadecimal en String
    private String convertirColor(Color color) {
        return String.format(
                "#%02X%02X%02X",
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
    }

    // Muestra un diálogo de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Actualiza la tabla de instrucciones limpiando los datos previos e ingresando nuevos.
     * @param listaInstrucciones Una lista de arreglos de objetos (o strings) donde cada posición es una columna: [Instrucción ASM, Código Binario]
     */
    public void actualizarTablaInstrucciones(List<Object[]> listaInstrucciones) {
        modeloTablaInstrucciones.setRowCount(0); // Borra las filas anteriores
        for (Object[] fila : listaInstrucciones) {
            modeloTablaInstrucciones.addRow(fila);
        }
    }

    /**
     * Actualiza la tabla de memoria RAM limpiando los datos previos e ingresando nuevos.
     * @param listaMemoria Una lista de arreglos de objetos donde cada posición es: [Posición, Instrucción ASM, Valor en Memoria]
     */
    public void actualizarTablaMemoria(List<Object[]> listaMemoria) {
        modeloTablaMemoria.setRowCount(0); // Borra las filas anteriores
        for (Object[] fila : listaMemoria) {
            modeloTablaMemoria.addRow(fila);
        }
    }

    // Getters y Setters
    public JButton getBtnSeleccionar() { return btnSeleccionar; }
    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnPasoAPaso() { return btnPasoAPaso; }
    public JButton getBtnEjecutar() { return btnEjecutar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnAplicarConfig() { return btnAplicarConfig; }

    public int getTamanoMemoriaSeleccionado() { return (int) spTamanoMemoria.getValue(); }
    public int getLimiteKernelSeleccionado() { return (int) spLimiteKernel.getValue(); }

    public DefaultTableModel getModeloTablaInstrucciones() { return modeloTablaInstrucciones; }
    public DefaultTableModel getModeloTablaMemoria() { return modeloTablaMemoria; }
    public JTable getTablaInstrucciones() { return tablaInstrucciones; }
    public JTable getTablaMemoria() { return tablaMemoria; }

    public void setEstadoBCP(String estado) { lblEstadoBCP.setText("ESTADO: " + estado); }
    public void setPC(String valor) { txtPC.setText(valor); }
    public void setIR(String valor) { txtIR.setText(valor); }
    public void setAC(String valor) { txtAC.setText(valor); }
    public void setAX(String valor) { txtAX.setText(valor); }
    public void setBX(String valor) { txtBX.setText(valor); }
    public void setCX(String valor) { txtCX.setText(valor); }
    public void setDX(String valor) { txtDX.setText(valor); }
}