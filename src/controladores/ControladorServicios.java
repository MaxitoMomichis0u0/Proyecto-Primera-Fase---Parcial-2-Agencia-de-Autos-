
package controladores;

import javax.swing.*;
import java.util.*;
import datos.servicioDAO;
import modelos.servicio;

public class ControladorServicios {
    
    private List <servicio> listaServicios;
    private servicioDAO consultaSQL;
    
     public ControladorServicios() {
        this.consultaSQL = new servicioDAO();
        this.listaServicios =  consultaSQL.listarServicios();
    }
    
     
     /*
     public void insertarServicio() {
         servicio s = new servicio();
         
         s.setFolio();
         s.setMarca();
         s.setAno();
         s.setCaracteristicas();
         
         try {
             
            if (consultaSQL.insertarAuto(a)) {
                JOptionPane.showMessageDialog(null, "Auto insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El auto NO fue insertado");
            }
             
         } catch (Exception error) {
             JOptionPane.showMessageDialog(null, "Error al hacer la operación: " +error);
         }
     } */
     
     /* Aquí puedes usar la listaAutos para mostrarlas en alguna parte de la vista.
     public void mostrarAutos() {
         
         
         
     } */
    
}
