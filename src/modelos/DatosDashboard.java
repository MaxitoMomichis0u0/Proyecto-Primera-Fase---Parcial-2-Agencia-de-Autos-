package modelos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Resumen de datos que muestra el dashboard. */
public class DatosDashboard {

    public int totalServicios, enEspera, enProceso, finalizados;
    public int clientes, autos, empleados;
    public double ingresosRefacciones;
    /** Mes (ej. "2026-05") -> servicios ingresados. */
    public Map<String, Integer> serviciosPorMes = new LinkedHashMap<>();
    /** Nombre de refaccion -> veces usada. */
    public Map<String, Integer> topRefacciones = new LinkedHashMap<>();
    /** Filas: folio, cliente, auto, estatus, fecha ingreso. */
    public List<String[]> recientes = new ArrayList<>();
    /** Filas: folio, cliente, auto, fecha proximo servicio. */
    public List<String[]> proximos = new ArrayList<>();
}
