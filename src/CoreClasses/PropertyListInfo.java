/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreClasses;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author korenciak.marek
 */
public class PropertyListInfo extends javax.swing.JFrame {

    /**
     * Informacne okno pre vypis listu vlastnictva
     */
    public PropertyListInfo(ArrayList<ArrayList> propListInfo) {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        propertyListCadasterIDLabel.setText(propListInfo.get(0).get(0) + "");
        propertyListCadasterNameLabel.setText((Integer)propListInfo.get(0).get(1)+"");
        propertyListIDLabel.setText(propListInfo.get(0).get(2) + "");

        DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();

        Object[] tablePut = new Object[2];

        for (int i = 0; i < propListInfo.get(1).size(); i++) {
            tablePut[0] = propListInfo.get(1).get(i);
            tablePut[1] = propListInfo.get(2).get(i);
            model.addRow(tablePut);
        }

        model = (DefaultTableModel) ownerTable.getModel();
        tablePut = new Object[7];

        for (int i = 0; i < propListInfo.get(3).size(); i++) {
            tablePut[0] = propListInfo.get(3).get(i);
            tablePut[1] = propListInfo.get(4).get(i);
            tablePut[2] = propListInfo.get(5).get(i);
            tablePut[3] = propListInfo.get(6).get(i);
            tablePut[4] = propListInfo.get(7).get(i);
            tablePut[5] = propListInfo.get(8).get(i);
            tablePut[6] = propListInfo.get(9).get(i);
            model.addRow(tablePut);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        propertyListCadasterIDLabel = new javax.swing.JLabel();
        propertyListCadasterNameLabel = new javax.swing.JLabel();
        propertyListIDLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        propertyTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ownerTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Property list");

        jLabel1.setText("Property list ID");

        jLabel2.setText("Cadaster ID");

        jLabel3.setText("Cadaster name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(propertyListCadasterNameLabel)
                            .addComponent(propertyListCadasterIDLabel)
                            .addComponent(propertyListIDLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(propertyListCadasterIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(propertyListCadasterNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(propertyListIDLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        propertyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Property ID", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        propertyTable.setEnabled(false);
        jScrollPane1.setViewportView(propertyTable);

        ownerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Person ID", "Name", "Surname", "Cadaster perm address", "Perm address ID", "Perm address", "Owner rate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ownerTable.setEnabled(false);
        jScrollPane2.setViewportView(ownerTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable ownerTable;
    private javax.swing.JLabel propertyListCadasterIDLabel;
    private javax.swing.JLabel propertyListCadasterNameLabel;
    private javax.swing.JLabel propertyListIDLabel;
    private javax.swing.JTable propertyTable;
    // End of variables declaration//GEN-END:variables
}
