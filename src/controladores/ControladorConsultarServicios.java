/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import datos.servicioDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import modelos.servicio;
import vistas.consultarservicio;

/**
 *
 * @author chuy
 */
public class ControladorConsultarServicios {
    private consultarservicio vista;
    private servicioDAO dao;

    public ControladorConsultarServicios(consultarservicio vista) {
        this.vista = vista;
        this.dao= new servicioDAO();
        cargarTabla();
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnEstado().addActionListener(e -> avanzarEstado());
        vista.getTxtFolio().getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
              
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
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
                s.getFolio(), s.getIdCliente(), s.getIdAuto(), s.getIdEmpleado(),
                s.getFechaIngreso(), s.getNombreEstado()
            });
        }
    }
        
        private void buscar(){
            String folio= vista.getTxtFolio().getText();
            dao.buscarPorFolio(folio);
        }
    private void filtrar(){
        String texto= vista.getTxtFolio().getText().toLowerCase();
        DefaultTableModel modelo= (DefaultTableModel) vista.getTablaServ().getModel();
        modelo.setRowCount(0);
        List<servicio> lista= dao.listarServicios();
        for(servicio s: lista){
            if(s.getFolio().toLowerCase().contains(texto)){
                modelo.addRow(new Object[]{
                    s.getId(), s.getFolio(), s.getIdCliente(), s.getIdAuto(),
                    s.getIdEmpleado(), s.getFechaIngreso(), s.getNombreEstado()
                });
            }
        }
        
    }
}
