/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import static FTP_Client.TextClient.dataPortClient;
import static FTP_Client.TextClient.output;
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
public class RenameFilesWindow extends javax.swing.JFrame {

    /**
     * Creates new form RenameFilesWindow
     */
    public RenameFilesWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    ClientWindow ClientPanel = new ClientWindow();
    ServerWindow ServerPanel = new ServerWindow();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SaveButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        PathText = new javax.swing.JTextField();
        main4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        SaveButton.setContentAreaFilled(false);
        SaveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaveButtonMouseClicked(evt);
            }
        });
        getContentPane().add(SaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 90, 30));

        CancelButton.setContentAreaFilled(false);
        CancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelButtonMouseClicked(evt);
            }
        });
        getContentPane().add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 90, 30));

        PathText.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        PathText.setToolTipText("");
        PathText.setBorder(null);
        getContentPane().add(PathText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 270, 40));

        main4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/panel rename.png"))); // NOI18N
        getContentPane().add(main4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

        //ClientPanel.setVisible(false);
        //ServerPanel.dispose();
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

    private void CancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();

    }//GEN-LAST:event_CancelButtonMouseClicked

    private void SaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();
        String dataTCP = "";
        String newPath = PathText.getText();

        if (newPath.isEmpty()) {
            errorWindow error = new errorWindow();
            error.setVisible(true);
        } else {
            try {
                PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(main.connection.getInputStream()));
                String s = "mkdir " + PathText.getText();
                if (s.startsWith("mkdir")) {
                    String[] command = s.split(" ");
                    String directory = command[1];
                    dataTCP = "MKD" + " " + directory; 							// MKD <SP> <pathname> <CRLF>
                    output.println(dataTCP);
                    System.out.println("Attempting to create directory: " + directory);
                    //System.out.println(input.readLine());
                    try {
                        String response = input.readLine();
                        System.out.println(response);
                        if (response.startsWith("200")) {
                            SuccessWindow success = new SuccessWindow();
                            success.setVisible(true);
                        } else {
                            errorWindow error = new errorWindow();
                            error.setVisible(true);
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SaveButtonMouseClicked

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
            java.util.logging.Logger.getLogger(RenameFilesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RenameFilesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RenameFilesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RenameFilesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RenameFilesWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JTextField PathText;
    private javax.swing.JButton SaveButton;
    private javax.swing.JLabel main4;
    // End of variables declaration//GEN-END:variables
}
