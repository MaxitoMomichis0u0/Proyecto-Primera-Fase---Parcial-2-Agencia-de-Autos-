
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
         s.setIdCliente();
         s.setIdAuto();
         s.setIdEmpleado();
         
         try {
             
            if (consultaSQL.insertarServicio(s)) {
                JOptionPane.showMessageDialog(null, "Servicio insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El servicio NO fue insertado");
            }
             
         } catch (Exception error) {
             JOptionPane.showMessageDialog(null, "Error al hacer la operación: " +error);
         }
     } 
     
     /*
     public void avanzarEstado(servicio s) {
         
         s.avanzarEstado();
         consultaSQL.actualizarEstatus(s);      
     } 
     */
     
     /*
     public void agregarRefacciones(servicio s, int idRef) {
         
         s.agregarRefaccion(idRef);
         consultaSQL.agregarRefaccionesAlServicio(s.getId(), idRef);
         
     }*/
     
     /*
     public void buscarPorFolio(String folio) {
         
         consultaSQL.buscarPorFolio(folio);
         
     }
        */
}
