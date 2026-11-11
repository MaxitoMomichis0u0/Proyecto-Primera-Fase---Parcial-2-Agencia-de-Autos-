/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import java.awt.*;
import vistas.principal;
import vistas.registrarservicio;
/**
 *
 * @author chuy
 */
public class ControladorPrincipal {
    private principal vista;

    public ControladorPrincipal(principal vista) {
        this.vista = vista;
        this.vista.getMenuRegServ().addActionListener(e -> mostrarVista(new registrarservicio()));
    }
                
                private void mostrarVista(javax.swing.JPanel panel){
                    vista.getContenedor().removeAll();
                    vista.getContenedor().add(panel, BorderLayout.CENTER);
                    vista.getContenedor().revalidate();
                    vista.getContenedor().repaint();
                }
                        
    
}
