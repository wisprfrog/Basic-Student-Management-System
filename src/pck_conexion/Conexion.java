package pck_conexion;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    private static Connection cnx;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String BD = "escuela";
    private static final String USER = "root";
    private static final String PASS = "BrandomGalder1?";
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD + "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public Conexion() {
        cnx = null;
    }

    public Connection getConexion() {
        cnx = null;
        try {
            Class.forName(DRIVER);
            cnx = (Connection) DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conversion de clase " + e.getMessage(), "ERROR", 0);
            System.exit(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion " + e.getMessage(), "ERROR", 0);
        }

        return cnx;
    }

    public void close() {
        try {
            cnx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexion " + e.getMessage(), "ERROR", 0);
        }
    }
}
