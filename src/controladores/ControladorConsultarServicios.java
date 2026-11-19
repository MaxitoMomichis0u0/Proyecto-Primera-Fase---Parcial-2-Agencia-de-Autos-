/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import datos.autoDAO;
import datos.clienteDAO;
import datos.empleadoDAO;
import datos.servicioDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import modelos.auto;
import modelos.cliente;
import modelos.empleado;
import modelos.servicio;
import vistas.consultarservicio;

/**
 *
 * @author chuy
 */
public class ControladorConsultarServicios {
    private consultarservicio vista;
    private servicioDAO dao;
    private clienteDAO clienteDao = new clienteDAO();
    private autoDAO autoDao = new autoDAO();
    private empleadoDAO empleadoDao = new empleadoDAO();
    private List<cliente> listaClientes;
    private List<auto> listaAutos;
    private List<empleado> listaEmpleados;
    public ControladorConsultarServicios(consultarservicio vista) {
        this.vista = vista;
        this.dao= new servicioDAO();
        listaClientes = clienteDao.listarClientes();
    listaAutos = autoDao.listarAutos();
    listaEmpleados = empleadoDao.listarEmpleados();

        cargarTabla();
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnEstado().addActionListener(e -> avanzarEstado());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getTxtFolio().getDocument().addDocumentListener(new DocumentListener(){
            
            @Override
            public void insertUpdate(DocumentEvent e) {
              filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              filtrar();
            }
        });
        
        vista.getTablaServ().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                
                seleccionarFila();
                
            }
        });
    }
    
    private void eliminar(){
        int fila= vista.getTablaServ().getSelectedRow();
        if(fila<0){
            JOptionPane.showMessageDialog(null, "Selecciona un servicio, porfas.");
            return;
        }
        int id= Integer.parseInt(vista.getTablaServ().getValueAt(fila, 0).toString());
        String folio= vista.getTablaServ().getValueAt(fila, 1).toString();

        int op= JOptionPane.showConfirmDialog(null,
                "¿Eliminar el servicio " + folio + " y sus refacciones asociadas?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(op != JOptionPane.YES_OPTION){
            return;
        }
        if(dao.eliminarServicio(id)){
            JOptionPane.showMessageDialog(null, "Se ha eliminado el servicio " + folio + " :D", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        }
    }

    public void avanzarEstado(){
        int fila= vista.getTablaServ().getSelectedRow();
        if(fila<0){
            JOptionPane.showMessageDialog(null, "Selecciona un servicio, porfas.");
            return;
        }
        int id= Integer.parseInt(vista.getTablaServ().getValueAt(fila, 0).toString());
        
        servicio s= dao.obtenerServicio(id);
        if(s!= null){
            s.avanzarEstado();
            dao.actualizarEstatus(s);
            cargarTabla();
        }
    }
    
    
    private void cargarTabla(){
        DefaultTableModel modelo= (DefaultTableModel) vista.getTablaServ().getModel();
        modelo.setRowCount(0);
        List<servicio> lista= dao.listarServicios();
        for(servicio s: lista){
            modelo.addRow(new Object[] {s.getId(),
                s.getFolio(), buscarCliente(s.getIdCliente()), buscarEmp(s.getIdEmpleado()), buscarAuto(s.getIdAuto()),
                s.getFechaIngreso(), s.getNombreEstado()
            });
        }
    }
        
        private void buscar(){
            String folio= vista.getTxtFolio().getText();
            dao.buscarPorFolio(folio);
        }
        
        private String buscarCliente(int id){
            for (cliente c: listaClientes){
                if(c.getId() == id){
                    return c.getNombre();
                }
            }
            return "";
        }
        private String buscarAuto(int id){
            for (auto c: listaAutos){
                if(c.getId() == id){
                    return c.getMarca()+ " " + c.getModelo();
                }
            }
            return "";
        }
        
        private String buscarEmp(int id){
            for (empleado c: listaEmpleados){
                if(c.getId() == id){
                    return c.getNombre();
                }
            }
            return "";
        }
    private void filtrar(){
        String texto= vista.getTxtFolio().getText().toLowerCase();
        DefaultTableModel modelo= (DefaultTableModel) vista.getTablaServ().getModel();
        modelo.setRowCount(0);
        List<servicio> lista= dao.listarServicios();
        for(servicio s: lista){
            if(s.getFolio().toLowerCase().contains(texto)){
                modelo.addRow(new Object[]{
                    s.getId(), s.getFolio(), buscarCliente(s.getIdCliente()), buscarAuto(s.getIdAuto()),
                    buscarEmp(s.getIdEmpleado()), s.getFechaIngreso(), s.getNombreEstado()
                });
            }
        }
        
    }
    
    private void seleccionarFila(){
        int fila= vista.getTablaServ().getSelectedRow();
        if(fila>=0){
            vista.getTxtFolio().setText(vista.getTablaServ().getValueAt(fila, 1).toString());
        }
    }
}
