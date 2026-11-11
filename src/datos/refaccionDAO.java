
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
        Connection cn
    }
    
}
