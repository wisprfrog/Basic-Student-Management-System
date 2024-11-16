package pck_vista;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pck_modelo.ClaseEstudiante;

public class FrmAdministrador extends javax.swing.JFrame {

    private final ClaseEstudiante CE;

    public FrmAdministrador() {
        initComponents();

        CE = new ClaseEstudiante();
        this.limpiar();
        cmbBoxCriterio.setSelectedIndex(-1);        
    }

    public int getComponenteInt(String componente) {
        int res = -1;

        switch (componente) {
            case "No cuenta":
                try {
                    res = Integer.parseInt(txtFieldNoCuenta.getText());

                    if (res < 0) {
                        JOptionPane.showMessageDialog(null, "El numero de cuenta debe ser positivo", "ERROR", 0);
                        txtFieldNoCuenta.setText("");
                        txtFieldNoCuenta.requestFocus();
                    }
                } catch (NumberFormatException e) {
                    res = -1;
                    JOptionPane.showMessageDialog(null, "El numero de cuenta debe ser numerico", "ERROR", 0);
                    txtFieldNoCuenta.setText("");
                    txtFieldNoCuenta.requestFocus();
                }
                break;

            case "Semestre":
                if (cmbBoxSemestre.getSelectedIndex() != -1) {
                    res = Integer.parseInt(cmbBoxSemestre.getSelectedItem().toString());
                } else {
                    res = -1;
                    JOptionPane.showMessageDialog(null, "Seleccione el semestre", "ERROR", 0);
                    cmbBoxSemestre.setSelectedIndex(-1);
                    cmbBoxSemestre.requestFocus();
                }

                break;

            case "Grupo":
                if (cmbBoxGrupo.getSelectedIndex() != -1) {
                    res = Integer.parseInt(cmbBoxGrupo.getSelectedItem().toString());
                } else {
                    res = -1;
                    JOptionPane.showMessageDialog(null, "Seleccione el grupo", "ERROR", 0);
                    cmbBoxGrupo.setSelectedIndex(-1);
                    cmbBoxGrupo.requestFocus();
                }

                break;
        }

        return res;
    }

    public String getComponenteString(String componente) {
        String res = null;

        switch (componente) {
            case "Nombre":

                res = txtFieldNombre.getText();

                if (res.isBlank()) {
                    res = null;
                    JOptionPane.showMessageDialog(null, "Debe escribir su nombre", "ERROR", 0);
                    txtFieldNombre.setText("");
                    txtFieldNombre.requestFocus();
                } else if (!res.matches(("(([A-Z]|[a-z])+[ ]{0,1})+"))) {
                    res = null;
                    JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras y", "ERROR", 0);
                    txtFieldNombre.setText("");
                    txtFieldNombre.requestFocus();
                }

                break;

            case "Programa educativo":
                if (cmbBoxProgEduc.getSelectedIndex() != -1) {
                    res = cmbBoxProgEduc.getSelectedItem().toString();
                } else {
                    res = null;
                    JOptionPane.showMessageDialog(null, "Seleccione el programa educativo", "ERROR", 0);
                    cmbBoxProgEduc.setSelectedIndex(-1);
                    cmbBoxProgEduc.requestFocus();
                }

                break;

            case "Estatus":
                if (radioBtnRegular.isSelected()) {
                    res = "Regular";
                }
                if (radioBtnIrregular.isSelected()) {
                    res = "Irregular";
                }

                //Si despues de lo anterior, res sigue siendo null, significa que no se selecciono ninguno
                if (res == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione su estatus", "ERROR", 0);
                    btnGroupEstatus.clearSelection();
                }

                break;

            case "Turno":
                if (radioBtnMatutino.isSelected()) {
                    res = "Matutino";
                }
                if (radioBtnVespertino.isSelected()) {
                    res = "Vespertino";
                }

                //Si despues de lo anterior, res sigue siendo null, significa que no se selecciono ninguno
                if (res == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione su turno", "ERROR", 0);
                    btnGroupTurno.clearSelection();
                }

                break;

            case "Salud":
                if (chckBoxSalud.isSelected()) {
                    res = "Cursado";
                } else {
                    res = "No cursado";
                }

                break;

            case "Artes":
                if (chckBoxArtes.isSelected()) {
                    res = "Cursado";
                } else {
                    res = "No cursado";
                }

                break;
        }

        return res;
    }

    public Double getComponenteDouble() {
        double res;

        try {
            res = Double.parseDouble(txtFieldPromedio.getText());

            if (res < 0.0) {
                JOptionPane.showMessageDialog(null, "El promedio debe ser positivo", "ERROR", 0);
                txtFieldPromedio.setText("");
                txtFieldPromedio.requestFocus();
            }
        } catch (NumberFormatException e) {
            res = -1.0;
            JOptionPane.showMessageDialog(null, "El promedio debe ser numerico", "ERROR", 0);
            txtFieldPromedio.setText("");
            txtFieldPromedio.requestFocus();
        }

        return res;
    }

    public java.sql.Date getComponenteDate(String componente) {
        java.sql.Date res = null;
        Date fechaD;
        long fechaConvert;

        switch (componente) {
            case "Ingreso":
                fechaD = dateChsrIngreso.getDate();

                if (fechaD != null) {
                    fechaConvert = fechaD.getTime();
                    res = new java.sql.Date(fechaConvert);
                } else {//res = null;
                    JOptionPane.showMessageDialog(null, "Registre una fecha de ingreso", "ERROR", 0);
                    dateChsrIngreso.cleanup();
                    dateChsrIngreso.requestFocus();
                }
                break;

            case "Egreso":
                fechaD = dateChsrEgreso.getDate();

                if (fechaD != null) {
                    fechaConvert = fechaD.getTime();
                    res = new java.sql.Date(fechaConvert);
                }
                //else -> res = null;
                break;
        }

        return res;
    }

    private void limpiar() {
        txtFieldNoCuenta.setText("");
        txtFieldNombre.setText("");
        cmbBoxSemestre.setSelectedIndex(-1);
        cmbBoxGrupo.setSelectedIndex(-1);
        txtFieldPromedio.setText("");
        cmbBoxProgEduc.setSelectedIndex(-1);
        btnGroupEstatus.clearSelection();
        btnGroupTurno.clearSelection();
        chckBoxSalud.setSelected(false);
        chckBoxArtes.setSelected(false);
        dateChsrIngreso.setDate(null);
        dateChsrEgreso.setDate(null);
        txtFieldNoCuenta.requestFocus();
    }

    private void listar() {
        tablaDatos.setModel(CE.getDatos());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupEstatus = new javax.swing.ButtonGroup();
        btnGroupTurno = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFieldNoCuenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFieldNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbBoxSemestre = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbBoxGrupo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtFieldPromedio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbBoxProgEduc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        radioBtnRegular = new javax.swing.JRadioButton();
        radioBtnIrregular = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        radioBtnMatutino = new javax.swing.JRadioButton();
        radioBtnVespertino = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        chckBoxSalud = new javax.swing.JCheckBox();
        chckBoxArtes = new javax.swing.JCheckBox();
        dateChsrIngreso = new com.toedter.calendar.JDateChooser();
        dateChsrEgreso = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        cmbBoxCriterio = new javax.swing.JComboBox<>();
        txtFieldCriterio = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Estudiantes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del estudiante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel1.setText("No. cuenta:");

        txtFieldNoCuenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setText("Nombre:");

        jLabel3.setText("Semestre:");

        cmbBoxSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));

        jLabel4.setText("Grupo");

        cmbBoxGrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));

        jLabel5.setText("Promedio general:");

        txtFieldPromedio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setText("Programa educativo:");

        cmbBoxProgEduc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lic. en Arquitectura", "Lic. en Biología", "Lic. en  Ciencias Computacionales", "Lic. en Ingeniería Civil", "Lic. en Ingeniería en Electrónica", "Lic. en Ingeniería Industrial", "Lic. en Ingeniería en Geología Ambiental", "Lic. en Química", "Lic. en Química de Alimentos" }));

        jLabel7.setText("Estatus:");

        btnGroupEstatus.add(radioBtnRegular);
        radioBtnRegular.setText("Regular");

        btnGroupEstatus.add(radioBtnIrregular);
        radioBtnIrregular.setText("Irregular");

        jLabel8.setText("Turno:");

        btnGroupTurno.add(radioBtnMatutino);
        radioBtnMatutino.setText("Matutino");

        btnGroupTurno.add(radioBtnVespertino);
        radioBtnVespertino.setText("Vespertino");
        radioBtnVespertino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnVespertinoActionPerformed(evt);
            }
        });

        jLabel9.setText("Fecha de ingreso:");

        jLabel10.setText("Fecha de egreso:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Talleres cursados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        chckBoxSalud.setText("Taller de salud");

        chckBoxArtes.setText("Taller de artes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chckBoxSalud, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chckBoxArtes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(chckBoxSalud)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chckBoxArtes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbBoxSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbBoxGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtFieldPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(cmbBoxProgEduc, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(radioBtnRegular, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(radioBtnIrregular, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(dateChsrIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(radioBtnMatutino, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioBtnVespertino, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(dateChsrEgreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFieldNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbBoxSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cmbBoxGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtFieldPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbBoxProgEduc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(radioBtnRegular)
                                .addComponent(radioBtnIrregular)
                                .addComponent(jLabel9))
                            .addComponent(dateChsrIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(radioBtnMatutino)
                                .addComponent(radioBtnVespertino)
                                .addComponent(jLabel10))
                            .addComponent(dateChsrEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        cmbBoxCriterio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No cuenta", "Nombre" }));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pck_iconos/buscarR.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pck_iconos/Reset6.PNG"))); // NOI18N
        btnReiniciar.setText("Reiniciar");
        btnReiniciar.setToolTipText("Reiniciar lista");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(cmbBoxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(txtFieldCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnBuscar)
                .addGap(34, 34, 34)
                .addComponent(btnReiniciar)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnBuscar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbBoxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFieldCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatos);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pck_iconos/guardar1.JPG"))); // NOI18N
        btnAgregar.setToolTipText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pck_iconos/guardar.JPG"))); // NOI18N
        btnActualizar.setToolTipText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pck_iconos/eliminar1.JPG"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(83, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAgregar)
                                    .addComponent(btnActualizar)
                                    .addComponent(btnEliminar))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnAgregar)
                        .addGap(48, 48, 48)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(44, 44, 44)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int res = 0;
        int nC = getComponenteInt("No cuenta");

        if (nC >= 0) {
            DefaultTableModel aux = CE.buscarDato(0, String.valueOf(nC));

            if (aux != null) {
                JOptionPane.showMessageDialog(null, "Ya existe un estudiante con dicho numero de cuenta", "ERROR", 0);
            } else {
                String nom = getComponenteString("Nombre");

                if (nom != null) {
                    int sem = getComponenteInt("Semestre");

                    if (sem >= 0) {
                        int gru = getComponenteInt("Grupo");

                        if (gru >= 0) {
                            String pE = getComponenteString("Programa educativo");

                            if (pE != null) {
                                double prom = getComponenteDouble();

                                if (prom >= 0.0) {
                                    String est = getComponenteString("Estatus");

                                    if (est != null) {
                                        String turno = getComponenteString("Turno");

                                        if (turno != null) {
                                            String sal = getComponenteString("Salud");

                                            if (sal != null) {
                                                String art = getComponenteString("Artes");

                                                if (art != null) {
                                                    java.sql.Date fechI = getComponenteDate("Ingreso");

                                                    if (fechI != null) {
                                                        java.sql.Date fechE = getComponenteDate("Egreso");

                                                        res = CE.insertarDatos(nC, nom, sem, gru, pE, prom, turno, est, sal, art, fechI, fechE);

                                                        if (res > 0) {
                                                            listar();
                                                            limpiar();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }

        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void radioBtnVespertinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnVespertinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBtnVespertinoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.listar();
    }//GEN-LAST:event_formWindowOpened

    private void tablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosMouseClicked
        int fila = tablaDatos.getSelectedRow();

        if (fila != -1) {
            this.limpiar();

            txtFieldNoCuenta.setText(tablaDatos.getValueAt(fila, 0).toString());
            txtFieldNombre.setText(tablaDatos.getValueAt(fila, 1).toString());
            cmbBoxSemestre.setSelectedIndex(Integer.parseInt(tablaDatos.getValueAt(fila, 2).toString()) - 1);
            cmbBoxGrupo.setSelectedIndex(Integer.parseInt(tablaDatos.getValueAt(fila, 3).toString()) - 1);
            cmbBoxProgEduc.setSelectedItem(tablaDatos.getValueAt(fila, 4));
            txtFieldPromedio.setText(tablaDatos.getValueAt(fila, 5).toString());

            if (tablaDatos.getValueAt(fila, 6).toString().equals("Matutino")) {
                radioBtnMatutino.setSelected(true);
            } else {
                radioBtnVespertino.setSelected(true);
            }

            if (tablaDatos.getValueAt(fila, 7).toString().equals("Regular")) {
                radioBtnRegular.setSelected(true);
            } else {
                radioBtnIrregular.setSelected(true);
            }

            if (tablaDatos.getValueAt(fila, 8).toString().equals("Cursado")) {
                chckBoxSalud.setSelected(true);
            } else {
                chckBoxSalud.setSelected(false);
            }

            if (tablaDatos.getValueAt(fila, 9).toString().equals("Cursado")) {
                chckBoxArtes.setSelected(true);
            } else {
                chckBoxArtes.setSelected(false);
            }

            String fechaI = tablaDatos.getValueAt(fila, 10).toString();
            Date fechaIng;

            try {
                fechaIng = new SimpleDateFormat("yyyy-MM-dd").parse(fechaI);

                dateChsrIngreso.setDate(fechaIng);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la fecha de ingreso\n" + e.getMessage(), "ERROR", 2);
            }

            String fechaE;
            try {
                fechaE = tablaDatos.getValueAt(fila, 11).toString();
            } catch (NullPointerException e) {
                fechaE = null;
            }

            Date fechaEg;
            if (fechaE == null) {
                fechaEg = null;
                dateChsrEgreso.setDate(fechaEg);
            } else {
                try {
                    fechaEg = new SimpleDateFormat("yyyy-MM-dd").parse(fechaE);

                    dateChsrEgreso.setDate(fechaEg);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar la fecha de ingreso\n" + e.getMessage(), "ERROR", 2);
                }
            }
        }
    }//GEN-LAST:event_tablaDatosMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        int indice = cmbBoxCriterio.getSelectedIndex();

        if (indice == -1) {
            JOptionPane.showMessageDialog(null, "Debe selecionar un criterio de busqueda", "ERROR", 0);
        } else {
            DefaultTableModel aux = CE.buscarDato(indice, txtFieldCriterio.getText());
            
            if(aux != null){
                tablaDatos.setModel(aux);
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        this.listar();
        cmbBoxCriterio.setSelectedIndex(-1);
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int res = 0;
        int nC = getComponenteInt("No cuenta");

        if (nC >= 0) {

            String nom = getComponenteString("Nombre");

            if (nom != null) {
                int sem = getComponenteInt("Semestre");

                if (sem >= 0) {
                    int gru = getComponenteInt("Grupo");

                    if (gru >= 0) {
                        String pE = getComponenteString("Programa educativo");

                        if (pE != null) {
                            double prom = getComponenteDouble();

                            if (prom >= 0.0) {
                                String est = getComponenteString("Estatus");

                                if (est != null) {
                                    String turno = getComponenteString("Turno");

                                    if (turno != null) {
                                        String sal = getComponenteString("Salud");

                                        if (sal != null) {
                                            String art = getComponenteString("Artes");

                                            if (art != null) {
                                                java.sql.Date fechI = getComponenteDate("Ingreso");

                                                if (fechI != null) {
                                                    java.sql.Date fechE = getComponenteDate("Egreso");

                                                    res = CE.actualizarDatos(nC, nom, sem, gru, pE, prom, turno, est, sal, art, fechI, fechE);

                                                    if (res > 0) {
                                                        listar();
                                                        limpiar();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }

        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int res = 0;
        int row = tablaDatos.getSelectedRow();
        
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Debe selecionar un registro de la tabla", "AVISO",1);
        }
        else{
            res = CE.eliminarRegistro(tablaDatos.getValueAt(row, 0).toString());
            if(res > 0){
                this.listar();
                this.limpiar();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        pck_conexion.Conexion cn = new pck_conexion.Conexion();
//
//        if (cn.getConexion() != null) {
//            System.out.println("Conexion correcta");
//
//            java.awt.EventQueue.invokeLater(new Runnable() {
//                public void run() {
//                    new FrmAdministrador().setVisible(true);
//                }
//            });
//        } else {
//            System.out.println("Conexion erronea");
//        }
//
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGroupEstatus;
    private javax.swing.ButtonGroup btnGroupTurno;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JCheckBox chckBoxArtes;
    private javax.swing.JCheckBox chckBoxSalud;
    private javax.swing.JComboBox<String> cmbBoxCriterio;
    private javax.swing.JComboBox<String> cmbBoxGrupo;
    private javax.swing.JComboBox<String> cmbBoxProgEduc;
    private javax.swing.JComboBox<String> cmbBoxSemestre;
    private com.toedter.calendar.JDateChooser dateChsrEgreso;
    private com.toedter.calendar.JDateChooser dateChsrIngreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioBtnIrregular;
    private javax.swing.JRadioButton radioBtnMatutino;
    private javax.swing.JRadioButton radioBtnRegular;
    private javax.swing.JRadioButton radioBtnVespertino;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTextField txtFieldCriterio;
    private javax.swing.JTextField txtFieldNoCuenta;
    private javax.swing.JTextField txtFieldNombre;
    private javax.swing.JTextField txtFieldPromedio;
    // End of variables declaration//GEN-END:variables
}
