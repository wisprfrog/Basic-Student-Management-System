package pck_modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pck_conexion.Conexion;

public class ClaseEstudiante {

    private PreparedStatement PS;
    private final Conexion CN;
    private DefaultTableModel DTM;
    private ResultSet RS;

    public ClaseEstudiante() {
        this.PS = null;
        this.CN = new Conexion();
    }
    
    private DefaultTableModel setTitulos(){
        DTM = new DefaultTableModel();
        DTM.addColumn("No cuenta");
        DTM.addColumn("Nombre");
        DTM.addColumn("Semestre");
        DTM.addColumn("Grupo");
        DTM.addColumn("Programa");
        DTM.addColumn("Promedio");
        DTM.addColumn("Turno");
        DTM.addColumn("Estatus");
        DTM.addColumn("T Salud");
        DTM.addColumn("T Artes");
        DTM.addColumn("Fecha Ingreso");
        DTM.addColumn("Fecha Egreso");
        
        return DTM;
    }
    
    public DefaultTableModel getDatos(){
        String SQL_SELECT = "SELECT * from estudiante";
        
        try{
            this.setTitulos();
            PS = CN.getConexion().prepareStatement(SQL_SELECT);
            RS = PS.executeQuery();
            Object[] fila = new Object[12];
            while(RS.next()){
                fila[0] = RS.getInt("noCuenta");
                fila[1] = RS.getString(2);
                fila[2] = RS.getInt(3);
                fila[3] = RS.getInt(4);
                fila[4] = RS.getString(5);
                fila[5] = RS.getDouble(6);
                fila[6] = RS.getString(7);
                fila[7] = RS.getString(8);
                fila[8] = RS.getString(9);
                fila[9] = RS.getString(10);
                fila[10] = RS.getDate(11);
                fila[11] = RS.getDate(12);
                DTM.addRow(fila);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al consultar estudiantes\n" + e.getMessage(), "ERROR DE CONSULTA", 2);
        }
        finally{
            PS = null;
            RS = null;
            CN.close();
        }
        
        return DTM;
    }

    public int insertarDatos(int noCuenta, String nombre, int semestre, int grupo, String progEd, double promedio, String turno, String status, String tallerSalud, String tallerArtes, Date fIngreso, Date fEgreso) {
        String SQL_INSERT = "INSERT INTO estudiante(noCuenta, nombre, semestre,"
            + " grupo, programaEducativo, promedio, turno, estatus, tallerSalud, tallerArtes, fechaIngreso, fechaEgreso)"
            + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int res = 0;

        try {
            setTitulos();
            PS = CN.getConexion().prepareStatement(SQL_INSERT);
            PS.setInt(1, noCuenta);
            PS.setString(2, nombre);
            PS.setInt(3, semestre);
            PS.setInt(4, grupo);
            PS.setString(5, progEd);
            PS.setDouble(6, promedio);
            PS.setString(7, turno);
            PS.setString(8, status);
            PS.setString(9, tallerSalud);
            PS.setString(10, tallerArtes);
            PS.setDate(11, fIngreso);
            PS.setDate(12, fEgreso);
            
            res = PS.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Registro exitoso", "ALTA", 1);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al guardar datos\n" + e.getMessage(), "ALTA", 1);
        }
        finally{
            PS = null;
            CN.close();
        }
        
        return res;
    }
    
    public DefaultTableModel buscarDato(int criterio, String parametro) {
        String SQL;
        
        if(criterio == 0){
            SQL = "SELECT * FROM estudiante WHERE noCuenta=" + parametro;
        }else{
            SQL = "SELECT * FROM estudiante WHERE nombre like '" + parametro + "%'"; //El porcentaje trae a todos aquellos que contengan a parametro al inicio de la cadena
        }
        
        try{
            this.setTitulos();
            PS = CN.getConexion().prepareStatement(SQL);
            RS = PS.executeQuery();
            Object[] fila = new Object[12];
            while(RS.next()){
                fila[0] = RS.getInt("noCuenta");
                fila[1] = RS.getString(2);
                fila[2] = RS.getInt(3);
                fila[3] = RS.getInt(4);
                fila[4] = RS.getString(5);
                fila[5] = RS.getDouble(6);
                fila[6] = RS.getString(7);
                fila[7] = RS.getString(8);
                fila[8] = RS.getString(9);
                fila[9] = RS.getString(10);
                fila[10] = RS.getDate(11);
                fila[11] = RS.getDate(12);
                DTM.addRow(fila);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al consultar estudiantes\n" + e.getMessage(), "ERROR DE CONSULTA", 2);
        }
        finally{
            if(DTM.getRowCount() == 0){
                DTM = null;
            }
            
            PS = null;
            RS = null;
            CN.close();
        }
        
        return DTM;
    }
    
    public int actualizarDatos(int noCuenta, String nombre, int semestre, int grupo, String progEd, double promedio, String turno, String status, String tallerSalud, String tallerArtes, Date fIngreso, Date fEgreso) {
        String SQL_UPDATE = "UPDATE estudiante SET noCuenta = ?, nombre = ?, semestre = ?,"
            + " grupo = ?, programaEducativo = ?, promedio = ?, turno = ?, estatus = ?, tallerSalud = ?, tallerArtes = ?, fechaIngreso = ?, fechaEgreso = ? WHERE noCuenta = ?";
        
        int res = 0;

        try {
            setTitulos();
            PS = CN.getConexion().prepareStatement(SQL_UPDATE);
            PS.setInt(1, noCuenta);
            PS.setString(2, nombre);
            PS.setInt(3, semestre);
            PS.setInt(4, grupo);
            PS.setString(5, progEd);
            PS.setDouble(6, promedio);
            PS.setString(7, turno);
            PS.setString(8, status);
            PS.setString(9, tallerSalud);
            PS.setString(10, tallerArtes);
            PS.setDate(11, fIngreso);
            PS.setDate(12, fEgreso);
            PS.setInt(13, noCuenta);
            
            res = PS.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Registro actualizado", "ACTUALIZAR", 1);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar datos\n" + e.getMessage(), "ERROR", 0);
        }
        finally{
            PS = null;
            CN.close();
        }
        
        return res;
    }
    
    public int eliminarRegistro(String noCuenta){
        int res = 0;
        String SQL_DELETE = "DELETE from estudiante WHERE noCuenta="+noCuenta;
        
        try{
            PS = CN.getConexion().prepareStatement(SQL_DELETE);
            res = PS.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Registro borrado con exito", "BORRAR", 1);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al borrar registro\n" + e.getMessage(), "ERROR", 0);
        }
        finally{
            PS = null;
            CN.close();
        }
        
        return res;
    }
}
