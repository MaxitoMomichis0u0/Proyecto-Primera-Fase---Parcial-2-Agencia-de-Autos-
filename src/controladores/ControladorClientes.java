
package controladores;

import java.util.*;
import datos.clienteDAO;
import javax.swing.*;
import modelos.cliente;
import datos.clienteDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import modelos.cliente;
import vistas.clientes;

public class ControladorClientes {
    
    private clientes vista;
    private clienteDAO dao;
    private List <cliente> listaClientes;
    private clienteDAO consultaSQL;
    
     public ControladorClientes(clientes vista) {
        this.vista= vista;
        this.dao= new clienteDAO();
        cargarTabla();
        
        this.vista.getBtnGuardar().addActionListener(e -> guardarCliente());
        this.vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getTablaClientes().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                seleccionarFila();
            }
        });
    }
         
     
     public void guardarCliente() {
     String nombre= vista.getTxtNombre().getText();
     String telefono= vista.getTxtTel().getText();
     cliente c= new cliente(0, nombre, telefono);
     dao.insertarCliente(c);
     cargarTabla();
     limpiarCampos();

     } 
     
    public void eliminar(){
        int id= Integer.parseInt(vista.getTxtId().getText());
        dao.eliminarCliente(id);
    }
     
     private void cargarTabla(){
         DefaultTableModel modelo= (DefaultTableModel) vista.getTablaClientes().getModel();
         modelo.setRowCount(0);
         
         List<cliente> lista= dao.listarClientes();
         for(cliente c: lista){
             modelo.addRow(new Object[]{
                 c.getId(), c.getNombre(), c.getTelefono()
             });
         }
     }
     
     private void limpiarCampos(){
         vista.getTxtNombre().setText("");
         vista.getTxtTel().setText("");
     }
    
}
