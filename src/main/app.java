/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controladores.ControladorLogin;
import vistas.login;

/**
 *
 * @author chuy
 */
public class app {
    
    public static void main(String[] args) {
           login log= new login();
    
        new ControladorLogin(log);
        log.setLocationRelativeTo(null);
        log.setVisible(true); 
    }

}
