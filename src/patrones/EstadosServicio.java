
package patrones;

import modelos.servicio;

public interface EstadosServicio {
    
    void AvanzarEstado(servicio serv);
    String getNombreEstado();
    
}
