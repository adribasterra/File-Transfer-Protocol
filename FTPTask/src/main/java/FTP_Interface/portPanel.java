/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import static FTP_Client.TextClient.dataPortClient;
import static FTP_Client.TextClient.output;
import static FTP_Interface.main.connection;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jojoj
 */
public class portPanel extends javax.swing.JFrame {

    /**
     * Creates new form portPanel
     */
    public portPanel() {
        initComponents();
        this.setLocationRelativeTo(null);
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PortText = new javax.swing.JTextField();
        okBuutoon = new javax.swing.JButton();
        portW = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setOpacity(0.0F);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PortText.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        PortText.setBorder(null);
        getContentPane().add(PortText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 250, 30));

        okBuutoon.setBorder(null);
        okBuutoon.setBorderPainted(false);
        okBuutoon.setContentAreaFilled(false);
        okBuutoon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        okBuutoon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okBuutoonMouseClicked(evt);
            }
        });
        getContentPane().add(okBuutoon, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 133, 70, 30));

        portW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/portpanel.png"))); // NOI18N
        getContentPane().add(portW, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 180));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        for (double i = 0.0; i <= 1.0; i = i + 0.1) {

            String val = i + "";
            float f = Float.valueOf(val);
            this.setOpacity(f);
            try {

                Thread.sleep(50);
            } catch (Exception e) {
            }

        }
    }//GEN-LAST:event_formWindowOpened

    private void okBuutoonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okBuutoonMouseClicked
        // TODO add your handling code here:
        this.dispose();

        String dataTCP = "";
        String currentDirectory = "files\\";
        boolean hasPort = false;

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);
            String s = "prt " + PortText.getText();
            if (s.startsWith("prt")) {
                String[] command = s.split(" ");
                if (command.length == 2) {
                    try {
                        dataPortClient = Integer.parseInt(command[1]);
                        dataTCP = "PRT" + " " + dataPortClient;				// PORT <SP> <host-port> <CRLF>
                        output.println(dataTCP);
                        hasPort = true;
                        try {
                            String response = input.readLine();
                            System.out.println(response);
                            if (response.startsWith("200")) {
                                //JOptionPane.showMessageDialog(this, "Success");
                                SuccessWindow success = new SuccessWindow();
                                success.setVisible(true);
                            } 
                            if (response.startsWith("503")) {
                                errorWindow error = new errorWindow();
                                error.setVisible(true);
                            } 
                             if (response.startsWith("220")) {
                                SuccessWindow success = new SuccessWindow();
                                success.setVisible(true);
                            }
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("It is not a number.");
                    }
                } else {
                    System.out.println("Format is not correct");
                }
                //System.out.println(input.readLine());
            }

        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_okBuutoonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(portPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(portPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(portPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(portPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new portPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField PortText;
    private javax.swing.JButton okBuutoon;
    private javax.swing.JLabel portW;
    // End of variables declaration//GEN-END:variables
}
