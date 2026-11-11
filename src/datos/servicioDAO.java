
package datos;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import modelos.servicio;
import patrones.*;

public class servicioDAO {

    conexion BD = conexion.getInstance();
    
    private EstadosServicio convertirTextoAEstado(String texto) {

        if (texto == null) {
            return new EstadoEnEspera();
        }

        switch (texto.toLowerCase()) {

            case "en espera":
                return new EstadoEnEspera();

            case "en proceso":
                return new EstadoEnProceso();

            case "finalizado":
                return new EstadoFinalizado();

            default:
                return new EstadoEnEspera();
        }
}
    
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
                    busq.getDate("fecha_siguiente_servicio")
                );
                
                lista.add(s);
                
                }    
                
            } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al listar: " +error);
        }
        
        BD.cerrarBD();
        return lista;
        
    }
    
    public void insertarServicio(servicio s) {
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement insertar = cn.prepareStatement("INSERT INTO servicios (folio, id_cliente, id_auto, id_empleado) VALUES (?,?,?,?)");
            
            insertar.setString(1, s.getFolio());
            insertar.setInt(2, s.getIdCliente());
            insertar.setInt(3, s.getIdAuto());
            insertar.setInt(4, s.getIdEmpleado());
            insertar.executeUpdate();
            
            
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al insertar servicio: " +error);
        }
        
        BD.cerrarBD();
        
    }
    
    public void actualizarEstatus(servicio s) {
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement actualizar = cn.prepareStatement("UPDATE servicios SET estatus = ? WHERE id = ?");
            
            actualizar.setString(1, s.getNombreEstado());
            actualizar.setInt(2, s.getId());
            actualizar.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Se ha avanzado de estatus corectamente");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error en la operación: " +error);
        }
        
    }
}
