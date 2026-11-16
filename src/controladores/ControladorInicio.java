/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import vistas.*;
import modelos.*;
/**
 *
 * @author chuy
 */
public class ControladorInicio {
    private Inicio c;
    private String usuario;

    public ControladorInicio(Inicio c) {
        this.c = c;
        cargarEmpleado();
    }
    
   private void cargarEmpleado(){
       c.getLblHola().setText("Bienvenid@, "+ Sesion.getUsuario());
   }
}
