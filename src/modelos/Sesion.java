/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author chuy
 */
public class Sesion {
    private static int id;
    private static String usuario;
    private static String correo;
    private static String rol;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Sesion.usuario = usuario;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Sesion.id = id;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        Sesion.correo = correo;
    }

    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        Sesion.rol = rol;
    }
    
    
    
}
