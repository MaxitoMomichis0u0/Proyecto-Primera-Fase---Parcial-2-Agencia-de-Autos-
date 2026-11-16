
package controladores;

import datos.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;
import vistas.*;
import modelos.*;
import java.text.*;
import java.util.*;
public class ControladorServicios {
    private registrarservicio vista;
    private servicioDAO servicioDao;
    private clienteDAO clienteDao;
    private autoDAO autoDao;
    private empleadoDAO empleadoDao;
    private refaccionDAO refaccionDao;
    private List<cliente> listaClientes;
    private List<auto> listaAutos;
    private List<empleado> listaEmpleados;

     public ControladorServicios(registrarservicio vista) {
         this.vista= vista;
         servicioDao= new servicioDAO();
         clienteDao= new clienteDAO();
         autoDao= new autoDAO();
         empleadoDao= new empleadoDAO();
         refaccionDao= new refaccionDAO();
         
         cargarAutos();
         cargarClientes();
         cargarEmpleados();
         cargarRefacciones();
         cargarTabla();
         this.vista.getBtnAggRef().addActionListener(e -> agregarRefaccion());
         this.vista.getBtnRegServ().addActionListener(e -> registrarServicio());
        this.vista.getTablaRef().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                seleccionarFila();
            }
        });
     
     }
     
     public void seleccionarFila(){
         
         
         int fila= vista.getTablaRef().getSelectedRow();
         if(fila >= 0){
         vista.getTxtId().setText(vista.getTablaRef().getValueAt(fila, 0).toString());
         vista.getTxtFolio().setText(vista.getTablaRef().getValueAt(fila, 1).toString());
         vista.getCboCliente().setSelectedItem(vista.getTablaRef().getValueAt(fila, 2).toString());
         vista.getCboAuto().setSelectedItem(vista.getTablaRef().getValueAt(fila, 3).toString());
         vista.getCboEmpleado().setSelectedItem(vista.getTablaRef().getValueAt(fila, 4).toString());
         vista.getTxtPersonaEntrega().setText(vista.getTablaRef().getValueAt(fila, 5).toString());
             try {
                 SimpleDateFormat formato= new SimpleDateFormat("yyyy-MM-dd");
                 Date fecha= formato.parse(vista.getTablaRef().getValueAt(fila, 6).toString());
                 vista.getJdcFecha().setDate(fecha);
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "No se pudo la fecha bro", "Error", JOptionPane.ERROR_MESSAGE);
             }
         
         vista.getCboRefaccion().setSelectedItem(vista.getTablaRef().getValueAt(fila, 7).toString());
         
         }
     }
     
     private void cargarClientes(){
         listaClientes= clienteDao.listarClientes();
         vista.getCboCliente().removeAllItems();
         for (cliente c: listaClientes){
             vista.getCboCliente().addItem(c.getNombre());
         }
     }
    private void cargarAutos(){
        listaAutos= autoDao.listarAutos();
        vista.getCboAuto().removeAllItems();
        for( auto a: listaAutos){
            vista.getCboAuto().addItem(a.getModelo());
        }
    }
    
    private void cargarEmpleados(){
        listaEmpleados= empleadoDao.listarEmpleados();
        vista.getCboEmpleado().removeAllItems();
        for( empleado e: listaEmpleados){
            vista.getCboEmpleado().addItem(e.getNombre());
        }
    }
     
    private void cargarRefacciones(){
        vista.getCboRefaccion().removeAllItems();
        for( refaccion a: refaccionDao.listarRefaccion()){
            vista.getCboRefaccion().addItem(a.getNombre());
        }
    }
    
    private void agregarRefaccion(){
        String nombre= vista.getCboRefaccion().getSelectedItem().toString();
        
        DefaultTableModel m= (DefaultTableModel) vista.getTablaRef().getModel();
        m.addRow(new Object[]{"",
        vista.getTxtFolio().getText(),
        vista.getCboCliente().getSelectedItem(),
        vista.getCboAuto().getSelectedItem(),
        vista.getCboEmpleado().getSelectedItem(),
        vista.getTxtPersonaEntrega().getText(),
        vista.getJdcFecha().getDate(),
        nombre});
    }

     
     private void registrarServicio(){

    try{
        String folio =vista.getTxtFolio().getText();
        String personaEntrega =vista.getTxtPersonaEntrega().getText();
        Date fechaProxima = vista.getJdcFecha().getDate();
        cliente clienteSel= listaClientes.get(vista.getCboCliente().getSelectedIndex());
        auto autoSel= listaAutos.get(vista.getCboAuto().getSelectedIndex());
        empleado empeladoSel= listaEmpleados.get(vista.getCboEmpleado().getSelectedIndex());
        int idCliente= clienteSel.getId();
        int idAuto= autoSel.getId();
        int idEmpleado= empeladoSel.getId();
        servicio s = new servicio( folio, idCliente, idAuto,  idEmpleado,  personaEntrega,     new Date(), fechaProxima );
        servicioDao.insertarServicio(s);
        JOptionPane.showMessageDialog( null, "Servicio registrado correctamente" );

    }catch(Exception e){
        JOptionPane.showMessageDialog( null,"Error: " + e.getMessage() );

    }

}
     
     
     private void cargarTabla(){
         DefaultTableModel modelo =(DefaultTableModel) vista.getTablaRef().getModel();
         modelo.setRowCount(0);
         List <servicio> lista= servicioDao.listarServicios();
         for(servicio s:lista){
             modelo.addRow(new Object[]{
                 s.getId(), s.getFolio(), s.getIdCliente(), s.getIdAuto(),
                 s.getIdEmpleado(), s.getPersonaEntrega(), s.getFechaProxServicio(),
                 s.getNombreEstado()
             });
         }
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
