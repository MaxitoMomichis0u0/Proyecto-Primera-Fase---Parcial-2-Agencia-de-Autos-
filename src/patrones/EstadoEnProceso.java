package patrones;

import modelos.servicio;

public class EstadoEnProceso implements EstadosServicio {

    @Override
    public void AvanzarEstado (servicio s) {
        // Al estar en proceso, el siguiente estado es "Finalizado"
        s.setEstatus(new EstadoFinalizado());
        System.out.println("El servicio pasó a: Finalizado");
    }

    @Override
    public String getNombreEstado() {
        return "En proceso";
    }
}