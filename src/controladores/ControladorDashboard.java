package controladores;

import datos.dashboardDAO;
import patrones.EventosServicio;
import patrones.Observador;
import vistas.dashboard;

/** Conecta el dashboard con los datos y lo suscribe como Observador de los servicios. */
public class ControladorDashboard implements Observador {

    private final dashboard vista;
    private final dashboardDAO dao = new dashboardDAO();

    public ControladorDashboard(dashboard vista) {
        this.vista = vista;
        this.vista.getBtnActualizar().addActionListener(e -> actualizar());

        // Suscribirse solo mientras el panel esta en pantalla.
        this.vista.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (vista.isShowing()) {
                    EventosServicio.getInstance().suscribir(this);
                    actualizar();
                } else {
                    EventosServicio.getInstance().desuscribir(this);
                }
            }
        });
        actualizar();
    }

    @Override
    public void actualizar() {
        vista.mostrar(dao.obtenerResumen());
    }
}
