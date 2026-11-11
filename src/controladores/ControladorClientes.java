
package controladores;

import java.util.*;
import datos.clienteDAO;
import javax.swing.*;
import modelos.cliente;

public class ControladorClientes {
    
    private List <cliente> listaClientes;
    private clienteDAO consultaSQL;
    
     public ControladorClientes() {
        this.consultaSQL = new clienteDAO();
        this.listaClientes =  consultaSQL.listarClientes();
    }
         
     /*
     public void insertarCliente() {
         cliente c = new cliente();
         
         c.setNombre();
         c.setTelefono();
     
         try {
             
            if (consultaSQL.insertarCliente(c)) {
                JOptionPane.showMessageDialog(null, "Cliente insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El cliente NO fue insertado");
            }
             
         } catch (Exception error) {
             JOptionPane.showMessageDialog(null, "Error al hacer la operación: " +error);
         }
     } */
    
}
