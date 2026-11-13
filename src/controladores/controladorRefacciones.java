/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import datos.refaccionDAO;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import modelos.refaccion;
import vistas.refaccionesconsultar;
/**
 *
 * @author chuy
 */
public class controladorRefacciones {
    private refaccionesconsultar vista;
    private refaccionDAO dao;

    public controladorRefacciones(refaccionesconsultar vista) {
        this.vista = vista;
        this.dao= new refaccionDAO();
        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnModificar().addActionListener(e -> modificar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getTablaRef().getSelectionModel().addListSelectionListener(e -> seleccionarFila());
        vista.getBtnLimpiar().addActionListener(e -> limpiar());
        this.vista.getTablaRef().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                seleccionarFila();
            }
        });
        cargarTabla();
    }
    
    private void guardar(){
        try {
            String nombre= vista.getTxtNombre().getText();
            double precio= Double.parseDouble(vista.getTxtPrecio().getText());
            
            refaccion r= new refaccion(0, nombre, precio);
            dao.insertarRefaccipn(r);
            JOptionPane.showMessageDialog(null,"Se ha registrado la refacción "+vista.getTxtNombre().getText()+ " :D", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiar();
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error, favor de ingresar los datos correctamente \nError: "+ e, "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    private void cargarTabla(){
        List<refaccion> lista= dao.listarRefaccion();
        DefaultTableModel modelo= new DefaultTableModel();
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        
        for(refaccion r: lista){
            modelo.addRow(new Object[]{
                r.getId(),
            r.getNombre(),
            r.getPrecio() 
            });
        }
        vista.getTablaRef().setModel(modelo);
    }
    
    private void seleccionarFila(){
        int fila= vista.getTablaRef().getSelectedRow();
        if(fila >= 0){
            vista.getTxtId().setText(vista.getTablaRef().getValueAt(fila, 0).toString());
            vista.getTxtNombre().setText(vista.getTablaRef().getValueAt(fila, 1).toString());
            vista.getTxtPrecio().setText(vista.getTablaRef().getValueAt(fila, 2).toString());

        }
    }
    
    private void modificar(){
        try {
            refaccion r= new refaccion(Integer.parseInt(vista.getTxtId().getText()), vista.getTxtNombre().getText(),
                    Double.parseDouble(vista.getTxtPrecio().getText()));
            dao.modificarRefaccion(r);
            JOptionPane.showMessageDialog(null,"Se ha actualizado la refacción "+vista.getTxtNombre().getText()+ " :D", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error \nError:"+ e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar(){
        try {
            int id= Integer.parseInt(vista.getTxtId().getText());
            dao.eliminarRefaccion(id);
            JOptionPane.showMessageDialog(null,"Se ha eliminado la refacción "+vista.getTxtNombre().getText()+ " :D", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiar();
        } catch (Exception e) {JOptionPane.showMessageDialog(null,"Ha ocurrido un error \nError:"+ e, "Error", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    private void limpiar(){
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
    }
    
    
}
