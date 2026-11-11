
package datos;

import java.util.*;
import java.sql.*;
import javax.swing.*;
import modelos.auto;
import patrones.conexion;

public class autoDAO {
    
    conexion BD = conexion.getInstance(); 
    
    public List <auto> listarAutos() {
        
        Connection cn= BD.conectarBD();
        List<auto> lista = new ArrayList<>();
        
        try {
            PreparedStatement seleccionarTodo = cn.prepareStatement("SELECT id, modelo, marca, ano, caracteristicas FROM autos");
            
            ResultSet busq = seleccionarTodo.executeQuery();
            
            while (busq.next()) {
               
                int id = busq.getInt("id");
                String modelo = busq.getString("modelo");
                String marca = busq.getString("marca");
                int ano = busq.getInt("ano");
                String caracteristicas = busq.getString("caracteristicas");
                
                auto a = new auto(id, modelo, marca, ano, caracteristicas);
                
                lista.add(a);
                
                }    
                
            } catch (Exception error) {
                
            JOptionPane.showMessageDialog(null, "Error al intentar crear la lista:" +error);
            
        }
        
        BD.cerrarBD();
        return lista;
        
    }
        
        
    public void insertarAuto(auto a) {
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement insertar = cn.prepareStatement("INSERT INTO autos (modelo, marca, ano, caracteristicas) VALUES (?,?,?,?)");
            
            insertar.setString(1, a.getModelo());
            insertar.setString(2, a.getMarca());
            insertar.setInt(3, a.getAno());
            insertar.setString(4, a.getCaracteristicas());
            
            insertar.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Auto registrado exitosamente.");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error:" +error);

        } 
        
        BD.cerrarBD();
        
    }
        
}
