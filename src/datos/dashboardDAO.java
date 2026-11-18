package datos;

import java.sql.*;
import javax.swing.JOptionPane;
import modelos.DatosDashboard;
import patrones.conexion;

public class dashboardDAO {

    conexion BD = conexion.getInstance();

    private int escalar(Connection cn, String sql) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public DatosDashboard obtenerResumen() {
        DatosDashboard d = new DatosDashboard();
        Connection cn = BD.conectarBD();

        try {
            d.clientes = escalar(cn, "SELECT COUNT(*) FROM clientes");
            d.autos = escalar(cn, "SELECT COUNT(*) FROM autos");
            d.empleados = escalar(cn, "SELECT COUNT(*) FROM empleados");

            try (PreparedStatement ps = cn.prepareStatement("SELECT estatus, COUNT(*) FROM servicios GROUP BY estatus");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String e = rs.getString(1) == null ? "" : rs.getString(1).toLowerCase();
                    int n = rs.getInt(2);
                    d.totalServicios += n;
                    switch (e) {
                        case "en proceso": d.enProceso += n; break;
                        case "finalizado": d.finalizados += n; break;
                        default: d.enEspera += n;
                    }
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT COALESCE(SUM(r.precio),0) FROM refaccion_por_servicio rs JOIN refacciones r ON r.id = rs.id_refaccion");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) d.ingresosRefacciones = rs.getDouble(1);
            }

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT DATE_FORMAT(fecha_ingreso,'%Y-%m') mes, COUNT(*) n FROM servicios "
                    + "WHERE fecha_ingreso >= DATE_SUB(CURDATE(), INTERVAL 5 MONTH) "
                    + "GROUP BY mes ORDER BY mes");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) d.serviciosPorMes.put(rs.getString("mes"), rs.getInt("n"));
            }

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT r.nombre, COUNT(*) n FROM refaccion_por_servicio rs JOIN refacciones r ON r.id = rs.id_refaccion "
                    + "GROUP BY r.id, r.nombre ORDER BY n DESC LIMIT 5");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) d.topRefacciones.put(rs.getString("nombre"), rs.getInt("n"));
            }

            String base = "SELECT s.folio, c.nombre cliente, CONCAT(a.marca,' ',a.modelo) auto, s.estatus, "
                    + "s.fecha_ingreso, s.fecha_siguiente_servicio FROM servicios s "
                    + "JOIN clientes c ON c.id = s.id_cliente JOIN autos a ON a.id = s.id_auto ";

            try (PreparedStatement ps = cn.prepareStatement(base + "ORDER BY s.fecha_ingreso DESC, s.id DESC LIMIT 6");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    d.recientes.add(new String[]{rs.getString("folio"), rs.getString("cliente"),
                        rs.getString("auto"), rs.getString("estatus"), String.valueOf(rs.getDate("fecha_ingreso"))});
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(base
                    + "WHERE s.fecha_siguiente_servicio >= CURDATE() ORDER BY s.fecha_siguiente_servicio LIMIT 5");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    d.proximos.add(new String[]{rs.getString("folio"), rs.getString("cliente"),
                        rs.getString("auto"), String.valueOf(rs.getDate("fecha_siguiente_servicio"))});
                }
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al cargar el dashboard: " + error.getMessage());
        }

        BD.cerrarBD();
        return d;
    }
}
