
package controladores;

import datos.*;
import javax.swing.*;
import javax.swing.table.*;
import vistas.*;
import modelos.*;
public class ControladorServicios {
    private registrarservicio vista;
    private servicioDAO servicioDao;
    private clienteDAO clienteDao;
    private autoDAO autoDao;
    private empleadoDAO empleadoDao;
    private refaccionDAO refaccionDao;
    

     public ControladorServicios(registrarservicio vista) {
         this.vista= vista;
         servicioDao= new servicioDAO();
         clienteDao= new clienteDAO();
         autoDao= new autoDAO();
         empleadoDao= new empleadoDAO();
         refaccionDao= new refaccionDAO();
         
         
         
         
         this.vista.getBtnAggRef().addActionListener(e -> agrtegarRefaccion());
         this.vista.getBtnEliminarRef().addActionListener(e -> eliminarREfaccion());
         this.vista.getBtnRegServ().addActionListener(e -> registrarServicio());
         
     
     }
  
     
     private void cargarClientes(){
         vista.getCboCliente().removeAllItems();
         for (cliente c: clienteDao.listarClientes()){
             vista.getCboCliente().addItem(c.getNombre());
         }
     }
    private void cargarAutos(){
        vista.getCboAuto().removeAllItems();
        for( auto a: autoDao.listarAutos()){
            vista.getCboAuto().addItem(a.getModelo());
        }
    }
    
    private void cargarEmpleados(){
        vista.getCboEmpleado().removeAllItems();
        for( empleado e: empleadoDao.listarEmpleados()){
            vista.getCboEmpleado().addItem(e.getNombre());
        }
    }
     
    private void cargarRefacciones(){
        vista.getCboRefaccion().removeAllItems();
        for( refaccion a: refaccionDao.listarRefaccion()){
            vista.getCboAuto().addItem(a.getNombre());
        }
    }
    
    private void agrtegarRefaccion(){
        String nombre= vista.getCboRefaccion().getSelectedItem().toString();
        
        DefaultTableModel m= (DefaultTableModel) vista.getTablaRef().getModel();
        m.addRow(new Object[] {"", nombre, ""});
    }
    
    private void eliminarREfaccion(){
        int fila= vista.getTablaRef().getSelectedRow();
        if(fila >=0){
            DefaultTableModel m= (DefaultTableModel) vista.getTablaRef().getModel();
            m.removeRow(fila);
        }
    }
            private void registrarServicio(){
            JOptionPane.showMessageDialog(null, "El servicio ha sido registrado.", "Servivio Registrado", JOptionPane.INFORMATION_MESSAGE);
        } 
     
     
     
     
     
     /*
     public void insertarServicio() {
         servicio s = new servicio();
         
         s.setFolio();
         s.setIdCliente();
         s.setIdAuto();
         s.setIdEmpleado();
         
         try {
             
            if (consultaSQL.insertarServicio(s)) {
                JOptionPane.showMessageDialog(null, "Servicio insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El servicio NO fue insertado");
            }
             
         } catch (Exception error) {
             JOptionPane.showMessageDialog(null, "Error al hacer la operación: " +error);
         }
     } 
     
     /*
     public void avanzarEstado(servicio s) {
         
         s.avanzarEstado();
         consultaSQL.actualizarEstatus(s);      
     } 
     */
     
     /*
     public void agregarRefacciones(servicio s, int idRef) {
         
         s.agregarRefaccion(idRef);
         consultaSQL.agregarRefaccionesAlServicio(s.getId(), idRef);
         
     }*/
     
     /*
     public void buscarPorFolio(String folio) {
         
         consultaSQL.buscarPorFolio(folio);
         
     }
        */
}
