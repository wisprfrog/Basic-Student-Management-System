package pck_escuela;

import pck_conexion.Conexion;
import pck_vista.FrmAdministrador;

public class Escuela {
    public static void main(String args[]){
        Conexion cn = new Conexion();
        FrmAdministrador FE = new FrmAdministrador();

        if (cn.getConexion() != null) {
            System.out.println("Conexion correcta");

            FE.setVisible(true);
            FE.setLocationRelativeTo(null);
        } else {
            System.out.println("Conexion erronea");
        }
    }
}
