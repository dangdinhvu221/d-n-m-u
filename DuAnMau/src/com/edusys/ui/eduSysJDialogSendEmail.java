/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Validate;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.edusys.utils.mesageDiaLogHelper;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 *
 * @author dangd
 */
public class eduSysJDialogSendEmail extends javax.swing.JDialog {

    /**
     * Creates new form eduSysJDialogSendEmail
     */
    public eduSysJDialogSendEmail(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    int randomCode;
    public void init() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        btnStatus();

    }

    NhanVienDAO dao = new NhanVienDAO();

    public void sendEmail() {
        try {
            NhanVien nhanVien = dao.selectByID(txtEnterUser.getText());
            if (Validate.checkEmty(this, txtEnterUser, "Vui lòng điền tài khoản!!!", "Error!!") == false) {
                return;
            } else if (nhanVien == null) {
                mesageDiaLogHelper.showErrorDialog(this, "Tài khoản này không tồn tại!!!", "Error!!");
                txtEnterUser.setBorder(new LineBorder(Color.RED));
                return;
            } else if (Validate.checkEmty(this, txtEmail, "Vui lòng điền email!!!", "Error!!") == false) {
                txtEnterUser.setBorder(new LineBorder(Color.WHITE));
                return;
            } else if (Validate.checkEmail(txtEmail, "Email không đúng định dạng", "Error!!") == false) {
                return;
            } else {
                Random rand = new Random();
                randomCode = rand.nextInt(999999);
                String host = "smtp.gmail.com";
                String user = "dangdinhvu221@gmail.com";
                String pass = "vu123456";
                String to = txtEmail.getText();
                String subject = "Reseting Code";
                String message = "Your reset code is " + randomCode;
                boolean sessionDebug = false;
                Properties props = System.getProperties();

                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.user", user);
                props.put("mail.smtp.password", pass);
                props.put("mail.smtp.port", "587");

                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(sessionDebug);
                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(user));
                InternetAddress[] address = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setText(message);
                Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, user, pass);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                mesageDiaLogHelper.showMessageDialog(null, "mã đã được gửi đến email", "Thông báo!");
                setTimeBtn();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mesageDiaLogHelper.showErrorDialog(this, "email không hợp lệ", "Error!!");
        }
    }

    Thread t;

    private void setTimeBtn() {
        btnVerifyCode.setEnabled(true);
        txtVerify.setEnabled(true);
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnSend.setEnabled(false);
                for (int i = 59; i >= 0; i--) {
                    txtTime.setText(i + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        break;
                    }
                }
                txtVerify.setEnabled(false);
                btnVerifyCode.setEnabled(false);
                btnSend.setEnabled(true);
            }
        });
        t.start();
    }

    public void VerifyCode() {
        if (Validate.checkEmty(this, txtVerify, "Vui lòng điền VerifyCode!!!", "Error!!") == false) {
            return;
        } else if (Integer.valueOf(txtVerify.getText()) != randomCode) {
            mesageDiaLogHelper.showErrorDialog(this, "Mã không đúng. Vui lòng nhập lại", "Error!!");
        } else {
            mesageDiaLogHelper.showMessageDialog(this, "Mã hợp lệ!", "Error!!");
            this.t.stop();
            btnVerifyCode.setEnabled(false);
            btnResetPass.setEnabled(true);
            txtPass.setEnabled(true);
            txtRePass.setEnabled(true);
        }
    }

    public void resetPass() {
        if (txtPass.getText().equals(txtRePass.getText())) {
            try {
                NhanVien nv = getForm();
                dao.updateResetPass(nv);
                mesageDiaLogHelper.showMessageDialog(this, "Reset Successfully", "Thông báo");
                this.dispose();
                new eduSysJDialogLogIn(null, true).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mesageDiaLogHelper.showErrorDialog(this, "password do not match", "Error!!");
        }
    }

    public NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMatKhau(txtPass.getText());
        nv.setMaNV(txtEnterUser.getText());
        return nv;
    }

    public void btnStatus() {
        txtPass.setEnabled(false);
        txtRePass.setEnabled(false);
        txtVerify.setEnabled(false);

        btnVerifyCode.setEnabled(false);
        btnResetPass.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSend = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtVerify = new javax.swing.JTextField();
        btnVerifyCode = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTime = new javax.swing.JLabel();
        txtEnterUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtRePass = new javax.swing.JPasswordField();
        btnResetPass = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnSend.setBackground(new java.awt.Color(0, 204, 51));
        btnSend.setForeground(new java.awt.Color(255, 0, 0));
        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email-png-icon-17.png"))); // NOI18N
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        jLabel1.setText("Enter Email");

        jLabel2.setText("Verify code");

        txtVerify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtVerifyMouseClicked(evt);
            }
        });
        txtVerify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtVerifyKeyReleased(evt);
            }
        });

        btnVerifyCode.setBackground(new java.awt.Color(0, 204, 51));
        btnVerifyCode.setForeground(new java.awt.Color(255, 0, 0));
        btnVerifyCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Payment.png"))); // NOI18N
        btnVerifyCode.setText("Verify Code");
        btnVerifyCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyCodeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/66456-mailang-icons-computer-logo-email-gmail.png"))); // NOI18N
        jLabel3.setText(" RESET PASSWORD");

        txtTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTime.setForeground(new java.awt.Color(255, 0, 0));
        txtTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Bell.png"))); // NOI18N

        txtEnterUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEnterUserMouseClicked(evt);
            }
        });

        jLabel4.setText("Enter user");

        jButton3.setBackground(new java.awt.Color(0, 204, 51));
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Left.png"))); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Mật khẩu mới:");

        jLabel6.setText("Nhập lại mật khẩu:");

        btnResetPass.setBackground(new java.awt.Color(0, 204, 51));
        btnResetPass.setForeground(new java.awt.Color(255, 0, 0));
        btnResetPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Refresh.png"))); // NOI18N
        btnResetPass.setText("Reset Password");
        btnResetPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRePass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEnterUser, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(338, 565, Short.MAX_VALUE))
                    .addComponent(txtEmail)
                    .addComponent(txtVerify)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVerifyCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResetPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEnterUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRePass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVerifyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnResetPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        sendEmail();
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnVerifyCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyCodeActionPerformed
        // TODO add your handling code here:
        VerifyCode();
    }//GEN-LAST:event_btnVerifyCodeActionPerformed

    private void txtVerifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtVerifyMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_txtVerifyMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new eduSysJDialogLogIn(null, true).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnResetPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPassActionPerformed
        // TODO add your handling code here:
        resetPass();
    }//GEN-LAST:event_btnResetPassActionPerformed

    private void txtEnterUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEnterUserMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_txtEnterUserMouseClicked

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        // TODO add your handling code here:
        if (txtEmail.getText().isEmpty()) {
            btnSend.setEnabled(true);
        }
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtVerifyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVerifyKeyReleased
        // TODO add your handling code here:
        if (txtVerify.getText().isEmpty()) {
            btnVerifyCode.setEnabled(true);
        }
    }//GEN-LAST:event_txtVerifyKeyReleased

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
            java.util.logging.Logger.getLogger(eduSysJDialogSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(eduSysJDialogSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(eduSysJDialogSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(eduSysJDialogSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                eduSysJDialogSendEmail dialog = new eduSysJDialogSendEmail(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResetPass;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnVerifyCode;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEnterUser;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtRePass;
    private javax.swing.JLabel txtTime;
    private javax.swing.JTextField txtVerify;
    // End of variables declaration//GEN-END:variables
}
