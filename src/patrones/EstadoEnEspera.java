
package patrones;

import modelos.servicio;

public class EstadoEnEspera implements EstadosServicio {

    @Override
    public void AvanzarEstado (servicio s) {
        s.setEstatus(new EstadoEnProceso());
        System.out.println("El servicio pasó a: En proceso");
    }

    @Override
    public String getNombreEstado() {
        return "En espera";
    }
}