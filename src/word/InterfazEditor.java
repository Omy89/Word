package word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazEditor extends JFrame {

    private JTextPane textPane;
    private JComboBox<String> comboFuentes, comboTamano, comboColores;
    private EditorDeTexto editorDeTexto;

    public InterfazEditor() {
        setTitle("Editor de Texto");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setSize(800, 800);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout());

        JButton juniorunforntie = new JButton("Nuevo Archivo");
        juniorunforntie.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(null, "Se borrará el texto no guardado. ¿Deseas continuar?",   "Confirmar acción",    JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE );
            if (opcion == JOptionPane.YES_OPTION) {
                editorDeTexto.getTextPane().setText(""); 
            }
        });
        panelControles.add(juniorunforntie);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> editorDeTexto.guardar());
        panelControles.add(botonGuardar);

        JButton botonAbrir = new JButton("Abrir Archivo");
        botonAbrir.addActionListener(e -> editorDeTexto.abrir());
        panelControles.add(botonAbrir);

        String[] fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboFuentes = new JComboBox<>(fuentes);
        panelControles.add(new JLabel("Fuente: "));
        panelControles.add(comboFuentes);

        String[] tamanos = {"8", "12", "16", "20", "24", "32", "48"};
        comboTamano = new JComboBox<>(tamanos);
        panelControles.add(new JLabel(" Tamaño: "));
        panelControles.add(comboTamano);

        String[] colores = {"Negro", "Rojo", "Azul", "Verde", "Amarillo"};
        comboColores = new JComboBox<>(colores);
        panelControles.add(new JLabel(" Color: "));
        panelControles.add(comboColores);

        principal.add(panelControles, BorderLayout.NORTH);

        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        principal.add(scrollPane, BorderLayout.CENTER);

        editorDeTexto = new EditorDeTexto(textPane, comboFuentes, comboTamano, comboColores);

        add(principal);
    }
}
