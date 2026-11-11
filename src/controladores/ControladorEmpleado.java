
package controladores;

import java.util.*;
import datos.empleadoDAO;
import javax.swing.*;
import modelos.empleado;
import vistas.*;

public class ControladorEmpleado {
    
    private empleadoDAO consultaSQL;

    public void validarLogin() {
        
        String correo = "";
        String contrasena = "";

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
    
}
