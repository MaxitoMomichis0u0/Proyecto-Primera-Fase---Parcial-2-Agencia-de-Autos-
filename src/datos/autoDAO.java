
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
    
    public void modificarAuto(auto autoModificar){
        
        Connection cn = BD.conectarBD();
        
        try {
            
            PreparedStatement actualizar = cn.prepareStatement("UPDATE autos SET modelo = ?, marca = ?, ano = ?, caracteristicas = ? WHERE id = ?");
            
            actualizar.setString(1, autoModificar.getModelo());
            actualizar.setString(2, autoModificar.getMarca());
            actualizar.setInt(3, autoModificar.getAno());
            actualizar.setString(4, autoModificar.getCaracteristicas());
            actualizar.setInt(5, autoModificar.getId());
            
            actualizar.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Auto actualizado exitosamente.");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error:" +error);

        } 
        
        BD.cerrarBD();
        
    }
    
    public void eliminarAuto(int id) {
        
        Connection cn = BD.conectarBD();
        
        try {
            PreparedStatement eliminar = cn.prepareStatement("DELETE FROM autos WHERE id = ?");
            eliminar.setInt(1, id);
            eliminar.executeUpdate(); 
                        
            JOptionPane.showMessageDialog(null, "Eliminación exitosa");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " +error);
        }
        
        BD.cerrarBD();
        
    }
    
    public void buscarAuto(int id) {
        
        Connection cn = BD.conectarBD();
        
        try {
            PreparedStatement buscar = cn.prepareStatement("SELECT * FROM autos WHERE id = ?");
            buscar.setInt(1,id);
            
            ResultSet busq = buscar.executeQuery();
                        
            while (busq.next()) {
                
                JOptionPane.showMessageDialog(null, "Auto encontrado \n ID: " +id+ "\nModelo: " +busq.getString("modelo")+ "\nMarca: " +busq.getString("marca")+ "\nAño: " +busq.getString("ano")+ "\nCaracteristicas: " +busq.getString("caracteristicas"));
               
            }
                        
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al buscar: " +error);
        }
        
    }
        
}
