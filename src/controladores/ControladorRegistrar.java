/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import datos.empleadoDAO;
import vistas.Registrar;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelos.empleado;
/**
 *
 * @author chuy
 */
public class ControladorRegistrar {
 private Registrar vista;
 private empleadoDAO dao;
 public ControladorRegistrar(Registrar vista) {
    this.vista = vista;
    this.dao = new empleadoDAO();
    cargar();
    this.vista.getBtnRegistrar().addActionListener( e -> registrar());
}



    private void cargar(){
                vista.getCboRol().removeAllItems();
        vista.getCboRol().addItem("Empleado");
        vista.getCboRol().addItem("Administrador");
    }
private void registrar(){
    if(vista.getTxtNombre().getText().trim().isEmpty()|| vista.getTxtCorreo().getText().trim().isEmpty()|| vista.getTxtPass().getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(null,"Completa todos los campos.");
        return;
    }
    try{

        empleado e =
            new empleado(0,
                vista.getTxtNombre().getText(),
                vista.getTxtCorreo().getText(),
                vista.getTxtPass().getText(),
                vista.getCboRol().getSelectedItem().toString());

        dao.insertarEmpleado(e);
        JOptionPane.showMessageDialog(null,"Empleado "+ vista.getTxtNombre().getText()+ " registrado correctamente");
        vista.dispose();

    }catch(Exception ex){
        JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
    }
}
                }
