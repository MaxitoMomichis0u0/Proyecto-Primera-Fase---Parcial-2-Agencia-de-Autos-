/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import datos.refaccionDAO;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        cargarTabla();
    }
    
    private void guardar(){
        try {
            String nombre= vista.getTxtNombre().getText();
            double precio= Double.parseDouble(vista.getTxtPrecio().getText());
            
            refaccion r= new refaccion(0, nombre, precio);
            dao.insertarRefaccipn(r);
            JOptionPane.showMessageDialog(null,"Se ha registrado la refacción :D", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();

                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error \nError:"+ e, "Error", JOptionPane.ERROR_MESSAGE);

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
    
    
}
