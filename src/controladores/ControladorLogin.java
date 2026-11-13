/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
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
        
                
    }
    
    private void iniciarSesion(){
        principal principalVista= new principal();
        ControladorPrincipal ctrPrincipal= new ControladorPrincipal(principalVista);
        principalVista.setSize(1300,750);
        principalVista.setLocationRelativeTo(null);
        principalVista.setVisible(true);
        vista.dispose();
    }
    
    
    
    
    
    
}
