package word;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.text.rtf.RTFEditorKit;

public class EditorDeTexto {

    private JTextPane textPane;
    private JComboBox<String> comboFuentes, comboTamano, comboColores;

    public EditorDeTexto(JTextPane textPane, JComboBox<String> comboFuentes, JComboBox<String> comboTamano, JComboBox<String> comboColores) {
        this.textPane = textPane;
        this.comboFuentes = comboFuentes;
        this.comboTamano = comboTamano;
        this.comboColores = comboColores;

        this.comboFuentes.addActionListener(e -> cambiarFuente());
        this.comboTamano.addActionListener(e -> cambiarTamano());
        this.comboColores.addActionListener(e -> cambiarColor());
    }

    public void cambiarFuente() {
        String fuenteSeleccionada = (String) comboFuentes.getSelectedItem();
        StyledDocument doc = textPane.getStyledDocument();
        Style estilo = textPane.addStyle("Fuente", null);
        StyleConstants.setFontFamily(estilo, fuenteSeleccionada);
        aplicarEstiloSeleccionado(doc, estilo);
    }

    public void cambiarTamano() {
        String tamanoStr = (String) comboTamano.getSelectedItem();
        int tamano = Integer.parseInt(tamanoStr);
        StyledDocument doc = textPane.getStyledDocument();
        Style estilo = textPane.addStyle("Tamaño", null);
        StyleConstants.setFontSize(estilo, tamano);
        aplicarEstiloSeleccionado(doc, estilo);
    }

    public void cambiarColor() {
        String colorSeleccionado = (String) comboColores.getSelectedItem();
        Color color = Color.BLACK;
        switch (colorSeleccionado) {
            case "Rojo":
                color = Color.RED;
                break;
            case "Azul":
                color = Color.BLUE;
                break;
            case "Verde":
                color = Color.GREEN;
                break;
            case "Amarillo":
                color = Color.YELLOW;
                break;
        }
        StyledDocument doc = textPane.getStyledDocument();
        Style estilo = textPane.addStyle("Color", null);
        StyleConstants.setForeground(estilo, color);
        aplicarEstiloSeleccionado(doc, estilo);
    }

    private void aplicarEstiloSeleccionado(StyledDocument doc, Style estilo) {
        int inicio = textPane.getSelectionStart();
        int fin = textPane.getSelectionEnd();
        if (inicio != fin) {
            doc.setCharacterAttributes(inicio, fin - inicio, estilo, false);
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un texto para aplicar el estilo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void guardar() {
        String name = JOptionPane.showInputDialog(null, "Escribe el nombre del archivo:", "Guardar Archivo", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del archivo no puede estar en blanco.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File mkdir =new File("Archivos de Texto");
        mkdir.mkdir();
        
        if (!name.endsWith(".rtf")) {
            name += ".rtf";
        }
        
        String nombreArchivo = "Archivos de texto/"+name;
        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "El archivo ya existe. ¿Deseas sobrescribirlo?", "Archivo Existente", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }try (FileOutputStream fos = new FileOutputStream(archivo)) {
            RTFEditorKit rtfEditorKit = new RTFEditorKit();
            StyledDocument txt = textPane.getStyledDocument();
            rtfEditorKit.write(fos, txt, txt.getStartPosition().getOffset(), txt.getLength());
            JOptionPane.showMessageDialog(null, "Archivo guardado correctamente como " + name, "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    public void abrir() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Archivos de Texto"));
    int seleccion = fileChooser.showOpenDialog(null);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivoSeleccionado = fileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(archivoSeleccionado)) {
            textPane.setText("");
            RTFEditorKit rtfEditorKit = new RTFEditorKit();
            StyledDocument doc = textPane.getStyledDocument();
            textPane.setDocument(doc);
            rtfEditorKit.read(fis, doc, 0);
            JOptionPane.showMessageDialog(null, "Archivo abierto correctamente: " + archivoSeleccionado.getName(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

    public JTextPane getTextPane() {
        return textPane;
    }

}
