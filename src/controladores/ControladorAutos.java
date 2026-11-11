
package controladores;

import java.util.*;
import datos.autoDAO;
import modelos.auto;

public class ControladorAutos {
    
    private List <auto> listaAutos;
    private autoDAO consultaSQL;
    
     public ControladorAutos() {
        this.consultaSQL = new autoDAO();
        this.listaAutos =  consultaSQL.listarAutos();
    }
    
}
