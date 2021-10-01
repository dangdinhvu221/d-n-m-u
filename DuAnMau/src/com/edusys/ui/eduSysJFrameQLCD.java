/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.entity.ChuyenDe;
import com.edusys.utils.Auth;
import com.edusys.utils.Validate;
import com.edusys.utils.XImages;
import com.edusys.utils.mesageDiaLogHelper;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dangd
 */
public class eduSysJFrameQLCD extends javax.swing.JDialog {

    /**
     * Creates new form eduSysJFrameQLCD
     */
    public eduSysJFrameQLCD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    ChuyenDeDAO dao = new ChuyenDeDAO();
    JFileChooser fileChooser = new JFileChooser();
    int row = -1;

    public void init() {
        setLocationRelativeTo(null);
        setResizable(false);

        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    public void insert() {
        if (Validate.checkTrungMaCD(txtMaChuyenDe.getText()) == true) {
            mesageDiaLogHelper.showErrorDialog(this, "Mã chuyên đề đã tồn tại!", "Error!!!");
            return;
        } else if (Validate.checkEmty(this, txtMaChuyenDe, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkSLKyTuCD(this, txtMaChuyenDe, "Mã chuyên đề không hợp lệ!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtTenChuyenDe, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtThoiLuong, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkNumber(this, txtThoiLuong, "Thời lượng không hợp lệ!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtHocPhi, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkNumber(this, txtHocPhi, "Học phí không hợp lệ!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmtylbl(this, lblAvatar, "Vui lòng chọn ảnh!!!", "Error!!!") == false) {
            return;
        } else {
            ChuyenDe cd = getForm();
            try {
                dao.insert(cd);
                this.fillTable();
                mesageDiaLogHelper.showMessageDialog(this, "Thêm mới thành công!!", "Thông báo!!!");
                this.clearForm();
            } catch (Exception e) {
                e.printStackTrace();
                mesageDiaLogHelper.showErrorDialog(this, "Thêm mới thất bại!!", "Error!!!");
            }
        }
    }

    public void update() {
        try {
            if (Validate.checkEmty(this, txtMaChuyenDe, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtTenChuyenDe, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtThoiLuong, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkNumber(this, txtThoiLuong, "Sai định dạng!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtHocPhi, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkNumber(this, txtHocPhi, "Sai định dạng!!!", "Error!!!") == false) {
                return;
            }
            ChuyenDe cd = getForm();
            dao.update(cd);
            this.fillTable();
            mesageDiaLogHelper.showMessageDialog(this, "Cập nhật thành công", "Thông báo!!");
        } catch (Exception e) {
            e.printStackTrace();
            mesageDiaLogHelper.showErrorDialog(this, "Cập nhật thất bại!!", "Error!!!");
        }
    }

    public void delete() {
        if (!Auth.isManager()) {
            mesageDiaLogHelper.showErrorDialog(this, "Bạn không có quyền xoá chuyên đề này!!", "Error");
        } else {
            String manv = txtMaChuyenDe.getText();
            if (mesageDiaLogHelper.showComfirmDialog(this, "Bạn thực sự muốn xoá nhân viên này!!!", "Thông báo") == JOptionPane.YES_OPTION) {
                try {
                    dao.delete(manv);
                    fillTable();
                    clearForm();
                    mesageDiaLogHelper.showMessageDialog(this, "Xoá thành công!!", "Thông báo!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    mesageDiaLogHelper.showErrorDialog(this, "Xoá thất bại!", "Error");
                }
            }
        }
    }

    public void clearForm() {
        ChuyenDe cd = new ChuyenDe();
        this.setForm(cd);
        lblAvatar.setIcon(null);
        this.row = -1;
        this.updateStatus();
    }

    public void edit() {
        String manv = tblQLCD.getValueAt(this.row, 0).toString();
        ChuyenDe cd = dao.selectByID(manv);
        this.setForm(cd);
        tabs.setSelectedIndex(0);
        this.updateStatus();
    }

    public void setForm(ChuyenDe cd) {
        txtMaChuyenDe.setText(cd.getMaCD());
        txtTenChuyenDe.setText(cd.getTenCD());
        txtThoiLuong.setText(cd.getThoiLuong() + "");
        txtHocPhi.setText(cd.getHocPhi() + "");
        txtMoTa.setText(cd.getMoTa());
        if (cd.getHinh() != null) {
            lblAvatar.setToolTipText(cd.getHinh());
            lblAvatar.setIcon(XImages.read(cd.getHinh()));
        }
    }

    public ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(txtMaChuyenDe.getText().toUpperCase());
        cd.setTenCD(txtTenChuyenDe.getText());
        cd.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
        cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        cd.setMoTa(txtMoTa.getText());
        cd.setHinh(lblAvatar.getToolTipText());
        return cd;
    }

    public void firts() {
        row = 0;
        this.edit();
    }

    public void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    public void next() {
        if (this.row < tblQLCD.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    public void last() {
        this.row = tblQLCD.getRowCount() - 1;
        this.edit();
    }

    public void fillTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblQLCD.getModel();
        tblModel.setRowCount(0);
        try {
            List<ChuyenDe> list = this.dao.selectAll();
            for (ChuyenDe cd : list) {
                tblModel.addRow(new Object[]{cd.getMaCD(), cd.getTenCD(), cd.getThoiLuong(), cd.getHocPhi(), cd.getMoTa(), cd.getHinh()});
            }
            tblModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            mesageDiaLogHelper.showErrorDialog(this, "Lỗi truy vấn dữ liệu!!", "Error!!");
        }
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblQLCD.getSelectedRow() - 1);

        txtMaChuyenDe.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirts.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !first);
    }

    public void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImages.save(file);// lưu hình vào thư mục logos
            ImageIcon icon = XImages.read(file.getName());// đọc hình từ logos
            lblAvatar.setIcon(icon);
            lblAvatar.setToolTipText(file.getName());// giữ tên hình trong tooltip
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

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblMaChuyenDe = new javax.swing.JLabel();
        txtMaChuyenDe = new javax.swing.JTextField();
        lblTenChuyenDe = new javax.swing.JLabel();
        txtTenChuyenDe = new javax.swing.JTextField();
        lblThoiLuong = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        lblHocPhi = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirts = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQLCD = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        lblAvatar.setBackground(new java.awt.Color(255, 255, 255));
        lblAvatar.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 255), null));
        lblAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvatarMouseClicked(evt);
            }
        });

        jLabel2.setText("Hình logo:");

        lblMaChuyenDe.setText("Mã chuyên đề");

        lblTenChuyenDe.setText("Tên chuyên đề");

        lblThoiLuong.setText("Thời lượng (giờ)");

        lblHocPhi.setText("Học phí");

        jLabel3.setText("Mô tả chuyên đề");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnLast.setText("|>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnFirts.setText("<|");
        btnFirts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirtsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaChuyenDe)
                            .addComponent(txtTenChuyenDe)
                            .addComponent(txtThoiLuong)
                            .addComponent(txtHocPhi)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaChuyenDe)
                                    .addComponent(lblTenChuyenDe)
                                    .addComponent(lblThoiLuong)
                                    .addComponent(lblHocPhi))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btnFirts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMaChuyenDe)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTenChuyenDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblThoiLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFirts))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblQLCD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLCD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLCDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblQLCD);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirtsActionPerformed
        // TODO add your handling code here:
        firts();
    }//GEN-LAST:event_btnFirtsActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblQLCDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLCDMouseClicked
        // TODO add your handling code here:
        row = tblQLCD.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblQLCDMouseClicked

    private void lblAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseClicked
        // TODO add your handling code here:
        chonAnh();
    }//GEN-LAST:event_lblAvatarMouseClicked

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
            java.util.logging.Logger.getLogger(eduSysJFrameQLCD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(eduSysJFrameQLCD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(eduSysJFrameQLCD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(eduSysJFrameQLCD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                eduSysJFrameQLCD dialog = new eduSysJFrameQLCD(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirts;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblMaChuyenDe;
    private javax.swing.JLabel lblTenChuyenDe;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblQLCD;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyenDe;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenChuyenDe;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
