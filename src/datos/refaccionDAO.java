
package datos;

import java.util.*;
import javax.swing.*;
import java.sql.*;
import patrones.conexion;
import modelos.refaccion;

public class refaccionDAO {
    
    conexion BD = conexion.getInstance(); 
    
    public List <refaccion> listarRefaccion() {
        
        Connection cn= BD.conectarBD();
        List<refaccion> lista = new ArrayList<>();
        
        try {
            PreparedStatement seleccionarTodo = cn.prepareStatement("SELECT id, nombre, precio FROM refacciones");
            
            ResultSet busq = seleccionarTodo.executeQuery();
            
            while (busq.next()) {
               
                int id = busq.getInt("id");
                String nombre = busq.getString("nombre");
                double precio = busq.getDouble("precio");
                
                refaccion r = new refaccion(id, nombre, precio);
                
                lista.add(r);
                
                }    
                
            } catch (Exception error) {
                
            JOptionPane.showMessageDialog(null, "Error al intentar crear la lista:" +error);
            
        }
        
        BD.cerrarBD();
        return lista;
        
    }
    public void insertarRefaccipn(refaccion r){
        Connection cn = BD.conectarBD();
        
        try {
            PreparedStatement insertar= cn.prepareStatement("insert into refacciones (nombre, precio) values (?,?)");
            insertar.setString(1, r.getNombre());
            insertar.setDouble(2, r.getPrecio());
            insertar.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error "+ e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        BD.cerrarBD();
    }
    
    public void modificarRefaccion(refaccion r){
    Connection cn= BD.conectarBD();
        try {
            PreparedStatement actualizar= cn.prepareStatement("update refacciones set nombre=?,precio=? where id=?");
            actualizar.setString(1, r.getNombre());
            actualizar.setDouble(2, r.getPrecio());
            actualizar.setInt(3, r.getId());
            
            actualizar.executeUpdate();
        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Ha ocurrido un error "+ e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        BD.cerrarBD();
    }
    
    public void eliminarRefaccion(int id){
        Connection cn= BD.conectarBD();
        try {
            PreparedStatement eliminar= cn.prepareStatement("delete from refacciones where id=?");
            eliminar.setInt(1, id);
            eliminar.executeUpdate();
                    
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"Ha ocurrido un error "+ e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        BD.cerrarBD();
    }
}
