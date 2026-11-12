
package controladores;

import java.util.*;
import datos.empleadoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.empleado;
import vistas.empleados;

public class ControladorEmpleado {
    
        private empleados vista;
        private empleadoDAO dao;
        
        
        
        
        /*
    public void validarLogin() {
        


        boolean validar = consultaSQL.validarLogin(correo, contrasena);

        if (validar == true) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido al sistema!");
    
            principal FrmPrincipal = new principal();
            FrmPrincipal.setSize(1300,775);
            FrmPrincipal.setLocationRelativeTo(null);
            FrmPrincipal.show(true);
            //this.dispose();
    
        } else {
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    */

    public ControladorEmpleado(empleados vista) {
        this.vista = vista;
        this.dao= new empleadoDAO();
        cargarRoles();
        cargarTabla();
        
        vista.getBtnGuardar().addActionListener(e -> guardarEmpleado());
        vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());
    }
    
    private void cargarRoles(){
        vista.getCboRol().removeAllItems();
        vista.getCboRol().addItem("Rol");
        vista.getCboRol().addItem("Rol");
        vista.getCboRol().addItem("Rol");
    }
    
    private void guardarEmpleado(){
        String nombre= vista.getTxtNombre().getText();
        String correo= vista.getTxtCorreo().getText();
        String pass= new String(vista.getTxtpass().getPassword());
        String rol= vista.getCboRol().getSelectedItem().toString();
        empleado e= new empleado(0, nombre, correo, correo, rol);
        dao.insertarEmpleado(e);
        cargarTabla();
        limpiarCampos();
    }
    
    private void cargarTabla(){
        DefaultTableModel m= (DefaultTableModel) vista.getTablaEmpleados().getModel();
        m.setRowCount(0);
        
        List<empleado> lista= dao.listarEmpleados();
        
        for (empleado e: lista){
            m.addRow(new Object[]{
                e.getId(), e.getNombre(), e.getCorreo(), e.getRol()
            });
        }
    }
    
    private void limpiarCampos(){
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtpass().setText("");
        vista.getCboRol().setSelectedIndex(0);
    }
}
