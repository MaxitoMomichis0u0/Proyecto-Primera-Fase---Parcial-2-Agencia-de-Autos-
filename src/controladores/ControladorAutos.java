
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
     
}
