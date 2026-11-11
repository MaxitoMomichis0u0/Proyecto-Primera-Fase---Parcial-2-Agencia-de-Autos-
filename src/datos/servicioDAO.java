
package datos;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import patrones.conexion;
import modelos.servicio;

public class servicioDAO {

    conexion BD = conexion.getInstance();
            
    public List<servicio> listarServicios() {
        
        Connection cn = BD.conectarBD();
        List<servicio> lista = new ArrayList<>();
        
        try {
            PreparedStatement seleccionarTodo = cn.prepareStatement("SELECT id, folio, id_cliente, id_auto, id_empleado, fecha_ingreso, estatus, fecha_siguiente_servicio FROM servicios");
            
            ResultSet busq = seleccionarTodo.executeQuery();
            
            while (busq.next()) {
               
                String estatus = busq.getString("estatus");
                EstadosServicio estado = convertirTextoAEstado(estatus);
                
                servicio s = new servicio(
                    busq.getInt("id"),
                    busq.getString("folio"),
                    busq.getInt("id_cliente"),
                    busq.getInt("id_auto"),
                    busq.getInt("id_empleado"),
                    busq.getDate("fecha_ingreso"),
                    estado,
                    busq.getDate("fecha_proximo_servicio")
                );
                
                lista.add(s);
                
                }    
                
            } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al listar: " +error);
        }
        
        return null;
    }
    
    
}
