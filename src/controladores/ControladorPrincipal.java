/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import java.awt.*;
import vistas.autos;
import vistas.clientes;
import vistas.consultarservicio;
import vistas.empleados;
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

    this.vista.getMenuRegServ()
        .addActionListener(e -> mostrarVista(new registrarservicio()));

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
        new ControladorAutos();
        mostrarVista(panel);
    });
    this.vista.getMenuEmpleados().addActionListener(e ->{
        empleados panel= new empleados();
        new ControladorEmpleado(panel);
        mostrarVista(panel);
     });
    this.vista.getMenuConServ().addActionListener(e ->{
        consultarservicio panel= new consultarservicio();
        new ControladorServicios(panel);
        mostrarVista(panel);
    });
}
                
                private void mostrarVista(javax.swing.JPanel panel){
                    vista.getContenedor().removeAll();
                    vista.getContenedor().add(panel, BorderLayout.CENTER);
                    vista.getContenedor().revalidate();
                    vista.getContenedor().repaint();
                }
                        
    
}
