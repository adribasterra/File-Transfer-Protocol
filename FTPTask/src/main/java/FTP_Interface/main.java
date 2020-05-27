/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import static FTP_Server.TextServer.listFiles;
import javax.swing.JOptionPane;

/**
 *
 * @author jojoj
 */
public class main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public main() {
        initComponents();
        this.setLocationRelativeTo(null);
        mdesenfoque.setVisible(false);
        ClientDesenfoque.setVisible(false);
        BothDesenfoque.setVisible(false);
        main.setVisible(true);
        SerDesButton.setVisible(false);
        ClientDesenfoque.setVisible(false);
    }
    
     ServerWindow ServerPanel = new ServerWindow();
    ClientWindow ClientPanel =  new ClientWindow();
    RenameFilesWindow RenamePanel = new RenameFilesWindow();

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SerDesButton = new javax.swing.JButton();
        ClienteBoton = new javax.swing.JButton();
        CieDesButton = new javax.swing.JButton();
        ServidorBoton = new javax.swing.JButton();
        mdesenfoque = new javax.swing.JLabel();
        BothDesenfoque = new javax.swing.JLabel();
        ClientDesenfoque = new javax.swing.JLabel();
        cerrar = new javax.swing.JLabel();
        main = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SerDesButton.setBorder(null);
        SerDesButton.setBorderPainted(false);
        SerDesButton.setContentAreaFilled(false);
        SerDesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SerDesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SerDesButtonMouseClicked(evt);
            }
        });
        getContentPane().add(SerDesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 200, 340));

        ClienteBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ClienteBoton.png"))); // NOI18N
        ClienteBoton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ClienteBoton.setBorderPainted(false);
        ClienteBoton.setContentAreaFilled(false);
        ClienteBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ClienteBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ClienteBotonRes.png"))); // NOI18N
        ClienteBoton.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ClienteBotonRes.png"))); // NOI18N
        ClienteBoton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ClienteBotonRes.png"))); // NOI18N
        ClienteBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClienteBotonMouseClicked(evt);
            }
        });
        getContentPane().add(ClienteBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 190, 340));

        CieDesButton.setBorder(null);
        CieDesButton.setBorderPainted(false);
        CieDesButton.setContentAreaFilled(false);
        CieDesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CieDesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CieDesButtonMouseClicked(evt);
            }
        });
        getContentPane().add(CieDesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, 200, 340));

        ServidorBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/servidorboton.png"))); // NOI18N
        ServidorBoton.setBorder(null);
        ServidorBoton.setBorderPainted(false);
        ServidorBoton.setContentAreaFilled(false);
        ServidorBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ServidorBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ServidorBotonRes.png"))); // NOI18N
        ServidorBoton.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ServidorBotonRes.png"))); // NOI18N
        ServidorBoton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/ServidorBotonRes.png"))); // NOI18N
        ServidorBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ServidorBotonMouseClicked(evt);
            }
        });
        getContentPane().add(ServidorBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 190, 340));

        mdesenfoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/Menu desenfocado.png"))); // NOI18N
        mdesenfoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mdesenfoqueMouseClicked(evt);
            }
        });
        getContentPane().add(mdesenfoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        BothDesenfoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/menu + server + client + botones+rename.png"))); // NOI18N
        BothDesenfoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BothDesenfoqueMouseClicked(evt);
            }
        });
        getContentPane().add(BothDesenfoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        ClientDesenfoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/cliente+desenfoque+rename.png"))); // NOI18N
        ClientDesenfoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClientDesenfoqueMouseClicked(evt);
            }
        });
        getContentPane().add(ClientDesenfoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        cerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarMouseClicked(evt);
            }
        });
        getContentPane().add(cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 70, 40));

        main.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/main.png"))); // NOI18N
        main.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainMouseClicked(evt);
            }
        });
        getContentPane().add(main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainMouseClicked
        // TODO add your handling code here:
        ClientPanel.dispose();
        ServerPanel.dispose();
        RenamePanel.dispose();

    }//GEN-LAST:event_mainMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        

    }//GEN-LAST:event_formWindowOpened

    private void ServidorBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ServidorBotonMouseClicked
        // TODO add your handling code here:
        ServerPanel.setVisible(true);
        main.setVisible(false);
         mdesenfoque.setVisible(true);
         ServidorBoton.setVisible(false);
         ClienteBoton.setVisible(false);
         ClientDesenfoque.setVisible(true);
        
    }//GEN-LAST:event_ServidorBotonMouseClicked

    private void ClienteBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClienteBotonMouseClicked
        // TODO add your handling code here:
       ClientPanel.setVisible(true);
        main.setVisible(false);
         mdesenfoque.setVisible(true);
         ServidorBoton.setVisible(false);
         ClienteBoton.setVisible(false);
         SerDesButton.setVisible(true);
    }//GEN-LAST:event_ClienteBotonMouseClicked

    private void cerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseClicked
        // TODO add your handling code here:
        
                
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "Do you want to close?","Exit",dialog);
        
        if(result == 0){
        
            System.exit(0);
        }
    }//GEN-LAST:event_cerrarMouseClicked

    private void mdesenfoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mdesenfoqueMouseClicked
        // TODO add your handling code here:
        RenamePanel.dispose();
        mdesenfoque.setVisible(false);
        main.setVisible(true);
        ServidorBoton.setVisible(true);
        ClienteBoton.setVisible(true);
        ServerPanel.dispose();
        ClientPanel.dispose();
        SerDesButton.setVisible(false);
        ClientDesenfoque.setVisible(false);
    }//GEN-LAST:event_mdesenfoqueMouseClicked

    private void ClientDesenfoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientDesenfoqueMouseClicked
        // TODO add your handling code here:
        RenamePanel.dispose();
        ClientDesenfoque.setVisible(false);
    }//GEN-LAST:event_ClientDesenfoqueMouseClicked

    private void BothDesenfoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BothDesenfoqueMouseClicked
        // TODO add your handling code here:
         RenamePanel.dispose();
        BothDesenfoque.setVisible(false);
    }//GEN-LAST:event_BothDesenfoqueMouseClicked

    private void SerDesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SerDesButtonMouseClicked
        // TODO add your handling code here:
        ServerPanel.setVisible(true);
        SerDesButton.setVisible(false);
    }//GEN-LAST:event_SerDesButtonMouseClicked

    private void CieDesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CieDesButtonMouseClicked
        // TODO add your handling code here:
        ClientPanel.setVisible(true);
       // ClientDesenfoque.setVisible(false);
    }//GEN-LAST:event_CieDesButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    /**
     * 
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BothDesenfoque;
    private javax.swing.JButton CieDesButton;
    private javax.swing.JLabel ClientDesenfoque;
    private javax.swing.JButton ClienteBoton;
    private javax.swing.JButton SerDesButton;
    private javax.swing.JButton ServidorBoton;
    private javax.swing.JLabel cerrar;
    private javax.swing.JLabel main;
    private javax.swing.JLabel mdesenfoque;
    // End of variables declaration//GEN-END:variables
}
