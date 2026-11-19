/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import java.awt.*;
import javax.swing.JOptionPane;
import modelos.Sesion;
import vistas.Inicio;
import vistas.autos;
import vistas.dashboard;
import vistas.clientes;
import vistas.consultarservicio;
import vistas.empleados;
import vistas.login;
import vistas.principal;
import vistas.refaccionesconsultar;
import vistas.registrarservicio;
/**
 *
 * @author chuy
 */
public class ControladorPrincipal {
    private principal vista;

public ControladorPrincipal(principal vista) {

    this.vista = vista;
     permisos();
    this.vista.getMenuRegServ()
    .addActionListener(e -> {
        registrarservicio panel = new registrarservicio();
        new ControladorServicios(panel);
        mostrarVista(panel);

    });
    this.vista.getMenuRefacciones()
        .addActionListener(e -> {
        refaccionesconsultar panel =new refaccionesconsultar();
        new controladorRefacciones(panel);
        mostrarVista(panel);
        });

    this.vista.getMenuClientes()
        .addActionListener(e -> {
            clientes panel = new clientes();
            new ControladorClientes(panel);
            mostrarVista(panel);
            });
    this.vista.getMenuAutos().addActionListener(e ->{
        autos panel= new autos();
        new ControladorAutos(panel);
        mostrarVista(panel);
    });
    this.vista.getMenuEmpleados().addActionListener(e ->{
        empleados panel= new empleados();
        new ControladorEmpleado(panel);
        mostrarVista(panel);
     });
    
    Inicio inicio= new Inicio();
    new ControladorInicio(inicio);
    mostrarVista(inicio);
    
    this.vista.getConServ().addActionListener(e ->{
        consultarservicio panel= new consultarservicio();
        new ControladorConsultarServicios(panel);
        mostrarVista(panel);
    });

    this.vista.getMenuDashboard().addActionListener(e -> {
        dashboard panel = new dashboard();
        new ControladorDashboard(panel);
        mostrarVista(panel);
    });

    this.vista.getMenuCerrar().addActionListener(e -> cerrarSesion());
    this.vista.getMenuSalir().addActionListener(e -> salir());
}
                
                private void mostrarVista(javax.swing.JPanel panel){
                    vista.getContenedor().removeAll();
                    vista.getContenedor().add(panel, BorderLayout.CENTER);
                    vista.getContenedor().revalidate();
                    vista.getContenedor().repaint();
                }
                
                private void cerrarSesion(){
                    login log = new login();
                    new ControladorLogin(log);
                    log.setLocationRelativeTo(log);
                    log.setVisible(true);
                    vista.dispose();
                }
                
                private void salir(){
                    int op= JOptionPane.showConfirmDialog(null, "¿De veras te quieres ir?", "Saliendo...", JOptionPane.YES_NO_OPTION);
                    if(op==JOptionPane.YES_OPTION){
                        Sesion.cerrarSesion();
                        System.exit(0);
                    }
                }
                        
    private void permisos(){
        if(Sesion.getRol().equalsIgnoreCase("Empleado")){
            vista.getMenuEmpleados().setVisible(false);
        }
    }
}
