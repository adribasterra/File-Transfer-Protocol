/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jojoj
 */
public class RegisterPanel extends javax.swing.JFrame {

    File f = new File("USERS");
    String Username, Emaill, Password;
    int ln;

    /**
     * Creates new form RegisterPanel
     */
    public RegisterPanel() {
        initComponents();
        this.setLocationRelativeTo(null);
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    /**
     * We use this method for create the folder where the txt with the user will
     * be
     */
    void CreateFolder() {

        //if file not exists, we create a new one
        if (!f.exists()) {
            f.mkdirs();

        }

    }

    /**
     * We use this method for check if the .txt is present or not, if not we
     * create a new one
     */
    void readFile() {
        try {
            FileReader fr = new FileReader(f + "\\logins.txt");
            System.out.println("file exists");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(f + "\\logins.txt");
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        ;

    }

    void addData(String usr, String pswd, String maill) {
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            try {
                for (int i = 0; i < ln; i++) {

                    raf.readLine();

                }
                raf.writeBytes("\r\n");
                raf.writeBytes("\r\n");
                raf.writeBytes("Username:" + usr + "\r\n");
                raf.writeBytes("Password:" + pswd + "\r\n");
                raf.writeBytes("Email:" + maill);
            } catch (IOException ex) {
                Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void CheckData(String usr, String pswd) {

        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            String line = raf.readLine();
            Username = line.substring(9);
            Password = raf.readLine().substring(9);
            Emaill = raf.readLine().substring(6);
            if (usr.equals(Username) & pswd.equals(Password)) {
                SuccessWindow success = new SuccessWindow();
                success.setVisible(true);
                System.out.println("SIIII");
            } else {
                errorWindow error = new errorWindow();
                error.setVisible(true);
                System.out.println("NOOOOOOOO");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void countLines() {

        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            for (int i = 0; raf.readLine() != null; i++) {
                ln++;
            }
            System.out.println("number of lines:" + ln);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void logic(String usr, String pswd) {

        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            for (int i = 0; i < ln;i+=4) {

                String forUsr = raf.readLine().substring(9);
                String forUPsw = raf.readLine().substring(9);
                if (usr.equals(forUsr) & pswd.equals(forUPsw)) {
                    SuccessWindow success = new SuccessWindow();
                    success.setVisible(true);
                    
                    break;
                } else if(i==(ln - 3)){
                    errorWindow error = new errorWindow();
                    error.setVisible(true);
                    
                }
                for (int k=1;k<=2;k++){
                raf.readLine();
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        closeButton = new javax.swing.JButton();
        passtetx = new javax.swing.JPasswordField();
        maintext = new javax.swing.JTextField();
        usernametext = new javax.swing.JTextField();
        RegisterButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        closeButton.setBorder(null);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        getContentPane().add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 10, 20, 20));

        passtetx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        passtetx.setForeground(new java.awt.Color(255, 255, 255));
        passtetx.setBorder(null);
        passtetx.setOpaque(false);
        getContentPane().add(passtetx, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 230, 30));

        maintext.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        maintext.setForeground(new java.awt.Color(255, 255, 255));
        maintext.setToolTipText("");
        maintext.setBorder(null);
        maintext.setOpaque(false);
        maintext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maintextActionPerformed(evt);
            }
        });
        getContentPane().add(maintext, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 230, 30));

        usernametext.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        usernametext.setForeground(new java.awt.Color(255, 255, 255));
        usernametext.setBorder(null);
        usernametext.setOpaque(false);
        getContentPane().add(usernametext, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 220, 30));

        RegisterButton.setBorder(null);
        RegisterButton.setBorderPainted(false);
        RegisterButton.setContentAreaFilled(false);
        RegisterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RegisterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterButtonMouseClicked(evt);
            }
        });
        getContentPane().add(RegisterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 100, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/Registerpanel.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 442));

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

    private void RegisterButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterButtonMouseClicked
        // TODO add your handling code here:

         String User = usernametext.getText();
        String Pass = passtetx.getText();

        if (User.isEmpty() || Pass.isEmpty()) {
            errorWindow error = new errorWindow();
            error.setVisible(true);
        } else {
         SuccessWindow success = new SuccessWindow();
        success.setVisible(true);
        CreateFolder();
        readFile();
        countLines();
        addData(usernametext.getText(), passtetx.getText(), maintext.getText());
        //CheckData("nilesh", "1234");
        //countlines();
        // addData(usernametext.getText(), maintext.getText(), passtetx.getText());
        //CheckData(usernametext.getText(),maintext.getText(),passtetx.getText());
        // JOptionPane.showMessageDialog(null, "Is registered in our system");
        //logic(usernametext.getText(), passtetx.getText());
        }


    }//GEN-LAST:event_RegisterButtonMouseClicked

    private void maintextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maintextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maintextActionPerformed

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        // TODO add your handling code here:
        LoginWindow login = new LoginWindow();
        this.dispose();
        login.setVisible(true);
    }//GEN-LAST:event_closeButtonMouseClicked

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
            java.util.logging.Logger.getLogger(RegisterPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RegisterButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField maintext;
    private javax.swing.JPasswordField passtetx;
    private javax.swing.JTextField usernametext;
    // End of variables declaration//GEN-END:variables
}
