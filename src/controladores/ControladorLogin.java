/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import datos.empleadoDAO;
import javax.swing.JOptionPane;
import modelos.Sesion;
import modelos.empleado;
import vistas.Registrar;
import vistas.login;
import vistas.principal;
/**
 *
 * @author chuy
 */
public class ControladorLogin {
    private login vista;

    public ControladorLogin(login vista) {
        this.vista = vista;
        this.vista.getBtnLogin().addActionListener(e -> iniciarSesion());
        this.vista.getBtnReg().addActionListener(e -> {
                Registrar reg= new Registrar(vista, true);
                new ControladorRegistrar(reg);
                reg.setLocationRelativeTo(vista);
                reg.setVisible(true);      
    });
    }
    
    private void iniciarSesion(){
       if(vista.getTxtUsuario().getText().isEmpty()
       && vista.getTxtpass().getText().isEmpty()){
        return;
    }
        String correo= vista.getTxtUsuario().getText();
        String pass= vista.getTxtpass().getText();
        empleadoDAO dao= new empleadoDAO();
        
        empleado emp= dao.obtenerEmpleado(correo, pass);
        if(emp != null){
            Sesion.setId(emp.getId());
            Sesion.setUsuario(emp.getNombre());
            Sesion.setCorreo(emp.getCorreo());
            Sesion.setRol(emp.getRol());
            Sesion.setUsuario(emp.getNombre());
        principal principalVista= new principal();
        JOptionPane.showMessageDialog(null, "Bienvenid@, "+ emp.getNombre() );
        ControladorPrincipal ctrPrincipal= new ControladorPrincipal(principalVista);
        principalVista.setSize(1300,750);
        principalVista.setLocationRelativeTo(null);
        principalVista.setVisible(true);
        vista.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }
    
    private void limpiar(){
        vista.getTxtUsuario().setText("");
        vista.getTxtpass().setText("");
    }
    
    
    
    
}
