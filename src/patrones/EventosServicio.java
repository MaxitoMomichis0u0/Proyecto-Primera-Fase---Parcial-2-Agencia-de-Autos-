package patrones;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Patrón Observer (Sujeto). Los DAO avisan aquí cuando se crea un servicio,
 * cambia su estado o se le agregan refacciones; el dashboard se suscribe
 * y se refresca solo.
 */
public class EventosServicio {

    private static EventosServicio instancia;
    private final List<Observador> observadores = new ArrayList<>();

    private EventosServicio() {
    }

    public static synchronized EventosServicio getInstance() {
        if (instancia == null) {
            instancia = new EventosServicio();
        }
        return instancia;
    }

    public void suscribir(Observador o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public void desuscribir(Observador o) {
        observadores.remove(o);
    }

    public void notificar() {
        List<Observador> copia = new ArrayList<>(observadores);
        SwingUtilities.invokeLater(() -> copia.forEach(Observador::actualizar));
    }
}
