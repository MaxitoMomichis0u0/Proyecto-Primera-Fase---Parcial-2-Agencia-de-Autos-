
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
    
     
         public void seleccionarFila(){
         int fila= vista.getTablaClientes().getSelectedRow();
         if(fila >= 0){
         vista.getTxtId().setText(vista.getTablaClientes().getValueAt(fila, 0).toString());
         vista.getTxtNombre().setText(vista.getTablaClientes().getValueAt(fila, 1).toString());
         vista.getTxtTel().setText(vista.getTablaClientes().getValueAt(fila, 2).toString());
         }
     }
         
             private void modificar(){
        try {
            cliente r= new cliente(Integer.parseInt(vista.getTxtId().getText()), vista.getTxtNombre().getText(),
                    vista.getTxtTel().getText());
            dao.modificarCliente(r);
            JOptionPane.showMessageDialog(null,"Se ha actualizado la refacción "+vista.getTxtNombre().getText()+ " :D", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error \nError:"+ e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar(){
        try {
            int id= Integer.parseInt(vista.getTxtId().getText());
            dao.eliminarCliente(id);
            JOptionPane.showMessageDialog(null,"Se ha eliminado la refacción "+vista.getTxtNombre().getText()+ " :D", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarCampos();
        } catch (Exception e) {JOptionPane.showMessageDialog(null,"Ha ocurrido un error \nError:"+ e, "Error", JOptionPane.ERROR_MESSAGE);
            
        }
    }
}
