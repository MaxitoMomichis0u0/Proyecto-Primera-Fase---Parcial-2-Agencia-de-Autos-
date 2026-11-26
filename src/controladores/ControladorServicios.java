
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
    private List<refaccion> listaRefacciones;
    private List<Integer> refaccionesSeleccionadas= new ArrayList<>();
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
        listaRefacciones= refaccionDao.listarRefaccion();
        vista.getCboRefaccion().removeAllItems();
        for( refaccion a: listaRefacciones){
            vista.getCboRefaccion().addItem(a.getNombre());
        }
    }
    
    private void agregarRefaccion(){
        int indice= vista.getCboRefaccion().getSelectedIndex();
        refaccion r= listaRefacciones.get(indice);
        refaccionesSeleccionadas.add(r.getId());
        
        DefaultTableModel m= (DefaultTableModel) vista.getTablaRef().getModel();
        m.addRow(new Object[]{"",
        vista.getTxtFolio().getText(),
        vista.getCboCliente().getSelectedItem(),
        vista.getCboAuto().getSelectedItem(),
        vista.getCboEmpleado().getSelectedItem(),
        vista.getTxtPersonaEntrega().getText(),
        vista.getJdcFecha().getDate(),
        r.getNombre()});
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
        int idServicio= servicioDao.obtenerUltimoServ();
        for(Integer idRef: refaccionesSeleccionadas){
        servicioDao.agregarRefaccionesAlServicio(idServicio, idRef);
    }
        String comprobante= armarComprobante(s, clienteSel, autoSel, empeladoSel);
        refaccionesSeleccionadas.clear();
        JOptionPane.showMessageDialog( null, "Servicio "+folio+" registrado correctamente" );
        new Comprobante(null, comprobante).setVisible(true);
        cargarTabla();
    }catch(Exception e){
        JOptionPane.showMessageDialog( null,"Error: " + e.getMessage() );

    }

}
     
     private String armarComprobante(servicio s, cliente c, auto a, empleado e){
         SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
         StringBuilder sb= new StringBuilder();
         sb.append("      AGENCIA DE AUTOMOVILES\n");
         sb.append("   COMPROBANTE DE SERVICIO\n");
         sb.append("==================================\n");
         sb.append("Folio:             ").append(s.getFolio()).append("\n");
         sb.append("Fecha de ingreso:  ").append(f.format(s.getFechaIngreso())).append("\n");
         sb.append("Estatus:           ").append(s.getNombreEstado()).append("\n");
         sb.append("----------------------------------\n");
         sb.append("CLIENTE\n");
         sb.append("  Nombre:   ").append(c.getNombre()).append("\n");
         sb.append("  Telefono: ").append(c.getTelefono()).append("\n");
         sb.append("VEHICULO\n");
         sb.append("  ").append(a.getMarca()).append(" ").append(a.getModelo()).append(" ").append(a.getAno()).append("\n");
         sb.append("Entregado por:     ").append(s.getPersonaEntrega()).append("\n");
         sb.append("Atendio:           ").append(e.getNombre()).append("\n");
         sb.append("Proximo servicio:  ").append(s.getFechaProxServicio() == null ? "-" : f.format(s.getFechaProxServicio())).append("\n");
         sb.append("----------------------------------\n");
         sb.append("REFACCIONES\n");
         double total= 0;
         if(refaccionesSeleccionadas.isEmpty()){
             sb.append("  (sin refacciones)\n");
         }
         for(Integer idRef: refaccionesSeleccionadas){
             for(refaccion r: listaRefacciones){
                 if(r.getId()==idRef){
                     sb.append(String.format("  %-22s $%9.2f\n", r.getNombre(), r.getPrecio()));
                     total+= r.getPrecio();
                 }
             }
         }
         sb.append("----------------------------------\n");
         sb.append(String.format("TOTAL REFACCIONES:    $%9.2f\n", total));
         return sb.toString();
     }

     private void cargarTabla(){
         DefaultTableModel modelo =(DefaultTableModel) vista.getTablaRef().getModel();
         modelo.setRowCount(0);
         List <servicio> lista= servicioDao.listarServicios();
         for(servicio s:lista){
             modelo.addRow(new Object[]{
                 s.getId(), s.getFolio(), buscarNombreCliente(s.getIdCliente()),buscarNombreAuto(s.getIdAuto()),
                 buscarNombreEmpleado(s.getIdEmpleado()), s.getPersonaEntrega(), s.getFechaProxServicio(),
                 buscarRefaccion(s.getId())
             });
         }
     }
     
     
     private String buscarNombreCliente(int id){
         for(cliente c: listaClientes){
             if(c.getId()==id){
                 return c.getNombre();
             }
         }
         return "";
     }
     
     private String buscarNombreAuto(int id){
         for(auto a: listaAutos){
             if(a.getId()==id){
                 return a.getModelo();
             }
         }
         return "";
     }
     
     private String buscarNombreEmpleado(int id){
         for(empleado e: listaEmpleados){
             if(e.getId()==id){
                 return e.getNombre();
             }
         }
         return "";
     }
     
     private String buscarRefaccion(int idS){
         return servicioDao.obtenerRefaccionServicio(idS);
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
