package patrones;

import modelos.servicio;
import javax.swing.JOptionPane;

public class EstadoFinalizado implements EstadosServicio {

    @Override
    public void AvanzarEstado(servicio s) {
        JOptionPane.showMessageDialog(null, "Este servicio ya se encuentra finalizado y no puede avanzar más.");
    }

    @Override
    public String getNombreEstado() {
        return "Finalizado";
    }
}