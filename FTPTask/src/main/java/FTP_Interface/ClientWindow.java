/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Interface;

import FTP_Client.DataClient;
import static FTP_Client.TextClient.dataPortClient;
import static FTP_Client.TextClient.logIn;
import static FTP_Client.TextClient.output;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jojoj
 */
public class ClientWindow extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public ClientWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        SendButton.setVisible(false);
    }

    ServerWindow ServerPanel = new ServerWindow();
    //RenameFilesWindow RenamePanel = new RenameFilesWindow();
    DirectorySWindow DirectPanel = new DirectorySWindow();
    String currentDirectory = "UserFiles\\";

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SendButton = new javax.swing.JButton();
        ComandField = new javax.swing.JTextField();
        RenameButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        UploadBuuton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        ClientLabel = new javax.swing.JLabel();

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

        SendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/Sendbutton2.png"))); // NOI18N
        SendButton.setBorder(null);
        SendButton.setBorderPainted(false);
        SendButton.setContentAreaFilled(false);
        SendButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SendButtonMouseClicked(evt);
            }
        });
        getContentPane().add(SendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 111, 42));

        ComandField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ComandField.setToolTipText("");
        ComandField.setBorder(null);
        getContentPane().add(ComandField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 170, 30));

        RenameButton.setBorder(null);
        RenameButton.setBorderPainted(false);
        RenameButton.setContentAreaFilled(false);
        RenameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RenameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RenameButtonMouseClicked(evt);
            }
        });
        getContentPane().add(RenameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 130, 50));

        DeleteButton.setBorder(null);
        DeleteButton.setBorderPainted(false);
        DeleteButton.setContentAreaFilled(false);
        DeleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteButtonMouseClicked(evt);
            }
        });
        getContentPane().add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 130, 40));

        downloadButton.setBorder(null);
        downloadButton.setBorderPainted(false);
        downloadButton.setContentAreaFilled(false);
        downloadButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        downloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadButtonMouseClicked(evt);
            }
        });
        getContentPane().add(downloadButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 130, 50));

        UploadBuuton.setBorder(null);
        UploadBuuton.setBorderPainted(false);
        UploadBuuton.setContentAreaFilled(false);
        UploadBuuton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UploadBuuton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UploadBuutonMouseClicked(evt);
            }
        });
        getContentPane().add(UploadBuuton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, 130, 40));

        addButton.setBorder(null);
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        getContentPane().add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 100, 40));

        ClientLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FTP_Images/Cliente panel.png"))); // NOI18N
        getContentPane().add(ClientLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
        //this.dispose();
        //ServerPanel.dispose();
        RenameFilesWindow RenamePanel = new RenameFilesWindow();
        RenamePanel.setVisible(true);

    }//GEN-LAST:event_addButtonMouseClicked

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

    private void UploadBuutonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UploadBuutonMouseClicked
        // TODO add your handling code here:
        //DirectPanel.setVisible(true);

        //We create the JFileChooser object
        JFileChooser fc = new JFileChooser();

        //We open the window and we save the selected option
        int selection = fc.showOpenDialog(this);

        //if the client press accept...
        if (selection == JFileChooser.APPROVE_OPTION) {

            //We select the file
            File file = fc.getSelectedFile();

            //Write the path of the file
            this.ComandField.setText(file.getAbsolutePath());
            SendButton.setVisible(true);

        }

    }//GEN-LAST:event_UploadBuutonMouseClicked

    private void downloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadButtonMouseClicked
        // TODO add your handling code here:
        int controlPort = 21;
        String dataTCP = "";
        
        boolean hasPort = true;
        try {

            //Thread.sleep(5 * 1000);
            //int port = 21
            // Connect with the server
            // Socket connection = new Socket("localhost", controlPort);
            //connection = ExitConection;
            // Recover input & output from connection
            PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);

            String s = "get " + ComandField.getText();
            if (s.startsWith("get")) {
                String[] command = s.split(" ");
                if (command.length == 2) {
                    String filename = command[1];
                    dataTCP = "RETR" + " " + filename; 						// RETR <SP> <pathname> <CRLF>
                    System.out.println("Attempting to get file: " + filename);
                    if (hasPort) {
                        DataClient.receiveFile(currentDirectory + filename, dataPortClient);
                        ///JOptionPane.showMessageDialog(this, "Success");
                    }
                } else {
                    System.out.println("Format is not correct");
                    JOptionPane.showMessageDialog(this, "Format is not correct");
                    dataTCP = "RETR";
                }
                output.println(dataTCP);
               // JOptionPane.showMessageDialog(this, "FILE ALREADY EXISTS IN CLIENT");
            }

        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_downloadButtonMouseClicked


    private void DeleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteButtonMouseClicked
        // TODO add your handling code here:
        int controlPort = 21;
        String dataTCP = "";
        
        boolean hasPort = true;
        try {

            //int port = 21
            // Connect with the server
            // Socket connection = new Socket("localhost", controlPort);
            //connection = ExitConection;
            // Recover input & output from connection
            PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(main.connection.getInputStream()));

            String s = "delete " + ComandField.getText();
            if (s.startsWith("delete")) {
                String[] command = s.split(" ");
                if (command.length == 2) {
                    String pathDirectory = command[1];
                    dataTCP = "DELE" + " " + pathDirectory; 
                    // DELE <SP> <pathname> <CRLF>
                } else {
                    dataTCP = "DELE";
                    JOptionPane.showMessageDialog(this, "Success");
                }
                System.out.println(dataTCP);
                JOptionPane.showMessageDialog(this, "this file dos not exist");
                //System.out.println(input.readLine());
                output.println(dataTCP);
                try {
                    String response = input.readLine();
                    System.out.println(response);
                } catch (IOException e) {
                    System.out.println(e);
                }
            };

        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DeleteButtonMouseClicked

    private void RenameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RenameButtonMouseClicked
        // TODO add your handling code here:
        //int controlPort = 21;
        String dataTCP = "";
        //String currentDirectory = "files\\";
        //boolean hasPort = true;
        try {

            //Thread.sleep(5 * 1000);
            //int port = 21
            // Connect with the server
            // Socket connection = new Socket("localhost", controlPort);
            //connection = ExitConection;
            // Recover input & output from connection
            BufferedReader input = new BufferedReader(new InputStreamReader(main.connection.getInputStream()));
            PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);
            String s = "rename " + ComandField.getText();
            if (s.startsWith("rename")) {
					String[] command = s.split(" ");
					if(command.length == 3){
						dataTCP = "RNFR" + " " + command[1] + " " + command[2];	// RNFR <SP> <pathname> <CRLF>
					}
					else { dataTCP = "RNFR"; }
					//System.out.println(input.readLine());
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}

        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_RenameButtonMouseClicked

    private void SendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendButtonMouseClicked
        // TODO add your handling code here:
        SendButton.setVisible(false);
        int controlPort = 21;
       
        String baseDirectory = "UserFiles\\";
        boolean hasPort = true;
        String dataTCP = "";
        try {

            //int port = 21
            // Connect with the server
            //Socket connection = new Socket("localhost", controlPort);
            //connection = ExitConection;
            // Recover input & output from connection
            PrintWriter output = new PrintWriter(main.connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(main.connection.getInputStream()));

            String s = "send " + ComandField.getText();
            if (s.startsWith("send")) {
                String[] command = s.split(" ");
                if (command.length == 2) {
                    String filename = command[1];
                    dataTCP = "STOR" + " " + filename; 						// STOR <SP> <pathname> <CRLF>
                    output.println(dataTCP);
                    try {
                        String response = input.readLine();
                        System.out.println(response);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    System.out.println("Attempting to send file: " + filename);
                    if (hasPort) {
                        DataClient.sendFile(filename, dataPortClient);
                        try {
                            String response = input.readLine();
                            System.out.println(response);
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                    //System.out.println(input.readLine());
                } else {
                    dataTCP = "STOR";
                    output.println(dataTCP);
                    JOptionPane.showMessageDialog(this, "Format is not correct.");

                    try {
                        String response = input.readLine();
                        System.out.println(response);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                //System.out.println(input.readLine());
            }
            //output.println("DELE " + s);

            //Ponemos a "Dormir" e
            Thread.sleep(5 * 1000);

            // output.println("END");
            // connection.close();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SendButtonMouseClicked

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
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientLabel;
    private javax.swing.JTextField ComandField;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton RenameButton;
    private javax.swing.JButton SendButton;
    private javax.swing.JButton UploadBuuton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton downloadButton;
    // End of variables declaration//GEN-END:variables
}
