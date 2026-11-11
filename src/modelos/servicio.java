
package modelos;

import java.util.*;
import patrones.EstadosServicio;

public class servicio {
    
   private int id;
   private String folio;
   private int idCliente;
   private int idAuto;
   private int idEmpleado;
   private Date fechaIngreso;
   private EstadosServicio estatus;
   private Date fechaProxServicio;
   private List<Integer> listaRefacciones;

    // Constructor para leer de la bdd. (sin refacciones)

    public servicio(int id, String folio, int idCliente, int idAuto, int idEmpleado, Date fechaIngreso, EstadosServicio estatus, Date fechaProxServicio) {
        this.id = id;
        this.folio = folio;
        this.idCliente = idCliente;
        this.idAuto = idAuto;
        this.idEmpleado = idEmpleado;
        this.fechaIngreso = fechaIngreso;
        this.estatus = estatus;
        this.fechaProxServicio = fechaProxServicio;
    }
   
    // Constructor para leer de la bdd. (con refacciones)

    public servicio(int id, String folio, int idCliente, int idAuto, int idEmpleado, Date fechaIngreso, EstadosServicio estatus, Date fechaProxServicio, List<Integer> listaRefacciones) {
        this.id = id;
        this.folio = folio;
        this.idCliente = idCliente;
        this.idAuto = idAuto;
        this.idEmpleado = idEmpleado;
        this.fechaIngreso = fechaIngreso;
        this.estatus = estatus;
        this.fechaProxServicio = fechaProxServicio;
        this.listaRefacciones = new ArrayList<>();
    }
   
    // Constructor para insertar en la bdd.
   
    public servicio(String folio, int idCliente, int idAuto, int idEmpleado, Date fechaIngreso, EstadosServicio estatus, Date fechaProxServicio, List<Integer> listaRefacciones) {
        this.folio = folio;
        this.idCliente = idCliente;
        this.idAuto = idAuto;
        this.idEmpleado = idEmpleado;
        this.fechaIngreso = fechaIngreso;
        this.estatus = estatus;
        this.fechaProxServicio = fechaProxServicio;
        this.listaRefacciones = new ArrayList<>();
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public EstadosServicio getEstatus() {
        return estatus;
    }

    public void setEstatus(EstadosServicio estatus) {
        this.estatus = estatus;
    }

    public Date getFechaProxServicio() {
        return fechaProxServicio;
    }

    public void setFechaProxServicio(Date fechaProxServicio) {
        this.fechaProxServicio = fechaProxServicio;
    }

    public List<Integer> getListaRefacciones() {
        return listaRefacciones;
    }

    public void setListaRefacciones(List<Integer> listaRefacciones) {
        this.listaRefacciones = listaRefacciones;
    }
    
    // Uso de patrón de diseño: State. Lo empleamos para cambiar entre estados de un servicio.

    
}
