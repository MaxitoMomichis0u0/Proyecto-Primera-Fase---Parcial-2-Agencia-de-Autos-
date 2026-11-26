package vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/** Ventana con el comprobante de datos basicos de un servicio registrado. */
public class Comprobante extends JDialog {

    private final JTextArea area = new JTextArea();

    public Comprobante(java.awt.Frame padre, String texto) {
        super(padre, "Comprobante de servicio", true);
        area.setText(texto);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setBorder(new EmptyBorder(12, 16, 12, 16));
        area.setCaretPosition(0);

        JButton btnImprimir = new JButton("Imprimir");
        JButton btnCerrar = new JButton("Cerrar");
        btnImprimir.addActionListener(e -> imprimir());
        btnCerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnImprimir);
        botones.add(btnCerrar);

        setLayout(new BorderLayout());
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        setSize(520, 520);
        setLocationRelativeTo(padre);
    }

    private void imprimir() {
        try {
            area.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo imprimir: " + ex.getMessage());
        }
    }
}
