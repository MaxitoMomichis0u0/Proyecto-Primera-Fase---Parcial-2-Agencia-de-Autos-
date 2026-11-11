
package controladores;

import java.util.*;
import datos.autoDAO;
import javax.swing.JOptionPane;
import modelos.auto;

public class ControladorAutos {
    
    private List <auto> listaAutos;
    private autoDAO consultaSQL;
    
     public ControladorAutos() {
        this.consultaSQL = new autoDAO();
        this.listaAutos =  consultaSQL.listarAutos();
    }
    
     // Esto es para que tú lo completes cuando hagas la vista, Chuy.
     
     /*
     public void insertarAuto() {
         auto a = new auto();
         
         a.setModelo();
         a.setMarca();
         a.setAno();
         a.setCaracteristicas();
         
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
     
     //bueno todo esto lo tienes que cambiar a tu gusto xd.
     
     public void modificarAuto(auto autoModificar) {
         
         for (auto a : listaAutos) {
            if (a.getId() == autoModificar.getId()) {
                a.setModelo(autoModificar.getModelo());
                a.setMarca(autoModificar.getMarca());
                a.setAno(autoModificar.getAno());
                a.setCaracteristicas(autoModificar.getCaracteristicas());
            }
        }
        
        consultaSQL.modificarAuto(autoModificar);
        
        JOptionPane.showMessageDialog(null, "Auto modificado exitosamente");
         
     }
     
     public void eliminarAuto(int id) {
         
         for (int i = 0; i < listaAutos.size(); i++) {
            if (listaAutos.get(i).getId() == id) {
                listaAutos.remove(i);
            }
        }
        
        consultaSQL.eliminarAuto(id);
        
        JOptionPane.showMessageDialog(null, "Auto eliminado exitosamente");
         
     }
     
     public void buscarAuto(int id) {
         
         consultaSQL.buscarAuto(id);
         
     }
     
}
