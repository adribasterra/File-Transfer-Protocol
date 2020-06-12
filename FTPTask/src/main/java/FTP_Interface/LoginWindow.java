/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author jojoj
 */
public class LoginWindow extends javax.swing.JFrame {

   
    
    /**
     * Creates new form LoginWindow
     */
    public LoginWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
        setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
    }
    

    RegisterPanel regi = new RegisterPanel();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cerrarButton = new javax.swing.JButton();
        registrerButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        passtext = new javax.swing.JPasswordField();
        userText = new javax.swing.JTextField();
        LOpanel = new javax.swing.JLabel();

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

        cerrarButton.setBorder(null);
        cerrarButton.setBorderPainted(false);
        cerrarButton.setContentAreaFilled(false);
        cerrarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarButtonMouseClicked(evt);
            }
        });
        getContentPane().add(cerrarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 10, 20, 20));

        registrerButton.setBorder(null);
        registrerButton.setBorderPainted(false);
        registrerButton.setContentAreaFilled(false);
        registrerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registrerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrerButtonMouseClicked(evt);
            }
        });
        getContentPane().add(registrerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 373, 40, 30));

        loginButton.setBorder(null);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
        getContentPane().add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 110, 40));

        passtext.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        passtext.setForeground(new java.awt.Color(255, 255, 255));
        passtext.setActionCommand("<Not Set>");
        passtext.setBorder(null);
        passtext.setOpaque(false);
        getContentPane().add(passtext, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 210, 30));

        userText.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        userText.setForeground(new java.awt.Color(255, 255, 255));
        userText.setToolTipText("");
        userText.setBorder(null);
        userText.setOpaque(false);
        getContentPane().add(userText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 220, 30));

        LOpanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/loginpanel.png"))); // NOI18N
        getContentPane().add(LOpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
         for (double i=0.0;i<=1.0;i = i+ 0.1){
        
           String val = i+ "";
           float f = Float.valueOf(val);
           this.setOpacity(f);
            try{
            
               Thread.sleep(50);
            }
            catch (Exception e){}
            
        }
    }//GEN-LAST:event_formWindowOpened

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        // TODO add your handling code here:
            /*data(user, pass);
    if(user.equals(userText.getText()) && pass.equals(passtext.getText())){
         main Main = new main();
         Main.setVisible(true);
         this.dispose();
    }else if(userText.getText().equals("") && passtext.getText().equals("")){
        JOptionPane.showMessageDialog(this,"User and / or Password are empty\nPlease enter them.");
        userText.setFocusable(true);
    }else if(userText.getText().equals("")){
        JOptionPane.showMessageDialog(this,"User is empty\nPlease enter it.");
        userText.setFocusable(true);
    }else if(passtext.getText().equals("")){
        JOptionPane.showMessageDialog(this,"Password is empty\nPlease enter it.");
        passtext.setFocusable(true);
    }
    else if(userText.getText().compareTo(user)!=0 && passtext.getText().compareTo(pass)!=0){
        JOptionPane.showMessageDialog(this,"Invalid username and / or password\nPlease enter them.");
         userText.setFocusable(true);
    }
    else if(userText.getText().compareTo(user)!=0){
        JOptionPane.showMessageDialog(this,"Invalid user\nPlease enter it.");
        userText.setFocusable(true);
    }else if(passtext.getText().compareTo(pass)!=0){
        JOptionPane.showMessageDialog(this,"Invalid password\nPlease enter it.");
        passtext.setFocusable(true);
    }*/
            regi.CreateFolder();
            regi.readFile();
            regi.countLines();
            regi.logic(userText.getText(), passtext.getText());
           // regi.logic(userText.getText(), passtext.getText());
        
    }//GEN-LAST:event_loginButtonMouseClicked

    private void registrerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrerButtonMouseClicked
        // TODO add your handling code here:
        RegisterPanel register = new RegisterPanel();
        register.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registrerButtonMouseClicked

    private void cerrarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();
        
    }//GEN-LAST:event_cerrarButtonMouseClicked

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
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOpanel;
    private javax.swing.JButton cerrarButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passtext;
    private javax.swing.JButton registrerButton;
    private javax.swing.JTextField userText;
    // End of variables declaration//GEN-END:variables
}
