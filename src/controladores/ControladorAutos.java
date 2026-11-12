
package controladores;

import java.util.*;
import datos.autoDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.auto;
import vistas.autos;

public class ControladorAutos {
    
    private autos vista;
    private autoDAO dao;
    

     public ControladorAutos(autos vista) {
         this.vista= vista;
         this.dao= new autoDAO();
         
         cargarTabla();
         vista.getBtnGuardar().addActionListener(e -> guardarAuto());
         vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());
    }
     
     private void guardarAuto(){
         auto a= new auto();
         
         a.setModelo(vista.getTxtModelo().getText());
         a.setMarca(vista.getTxtMarca().getText());
         a.setAno(Integer.parseInt(vista.getTxtAno().getText()));
         a.setCaracteristicas(vista.getTxtCarac().getText());
         dao.insertarAuto(a);
         cargarTabla();
         limpiarCampos();
     }
     
     private void cargarTabla(){
         DefaultTableModel  m= (DefaultTableModel) vista.getTablaAutos().getModel();
         m.setRowCount(0);
         List<auto> lista= dao.listarAutos();
         
         for(auto a: lista){
             
             m.addRow(new Object[]{
                 a.getId(), a.getModelo(), a.getMarca(), a.getAno(), a.getCaracteristicas()
             });
         }
     }
     
     private void limpiarCampos(){
         vista.getTxtId().setText("");
         vista.getTxtModelo().setText("");
         vista.getTxtMarca().setText("");
         vista.getTxtAno().setText("");
         vista.getTxtCarac().setText("");
     }
     
     
    
     // Esto es para que tú lo completes cuando hagas la vista, Chuy. no vua borrar esto xd
     
     /*
     public void insertarAuto() {
         auto a = new auto();
         
         a.setModelo();
         a.setMarca();
         a.setAno();
         a.setCaracteristicas();
         
         try {
             
            if (consultaSQL.insertarAuto(a)) {
                JOptionPane.showMessageDialog(null, "Auto insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El auto NO fue insertado");
            }
             
         } catch (Exception error) {
             JOptionPane.showMessageDialog(null, "Error al hacer la operación: " +error);
         }
     } */
     
     /* Aquí puedes usar la listaAutos para mostrarlas en alguna parte de la vista.
     public void mostrarAutos() {
         
         
         
     } */
     
     //bueno todo esto lo tienes que cambiar a tu gusto xd.
     /*
     public void modificarAuto(auto autoModificar) {
         
         for (auto a : listaAutos) {
            if (a.getId() == autoModificar.getId()) {
                a.setModelo(autoModificar.getModelo());
                a.setMarca(autoModificar.getMarca());
                a.setAno(autoModificar.getAno());
                a.setCaracteristicas(autoModificar.getCaracteristicas());
            }
        }
        
        consultaSQL.modificarAuto(autoModificar);
        
        JOptionPane.showMessageDialog(null, "Auto modificado exitosamente");
         
     }
     
     public void eliminarAuto(int id) {
         
         for (int i = 0; i < listaAutos.size(); i++) {
            if (listaAutos.get(i).getId() == id) {
                listaAutos.remove(i);
            }
        }
        
        consultaSQL.eliminarAuto(id);
        
        JOptionPane.showMessageDialog(null, "Auto eliminado exitosamente");
         
     }
     
     public void buscarAuto(int id) {
         
         consultaSQL.buscarAuto(id);
         
     }
     */
}
