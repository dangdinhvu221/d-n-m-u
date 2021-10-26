/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.NguoiHocDAO;
import com.edusys.entity.NguoiHoc;
import com.edusys.utils.Auth;
import com.edusys.utils.Validate;
import com.edusys.utils.XDate;
import com.edusys.utils.mesageDiaLogHelper;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Đặng Đình Vũ
 */
public class eduSysJInternalQLNH extends javax.swing.JInternalFrame {

    /**
     * Creates new form eduSysJInternalQLNH
     */
    public eduSysJInternalQLNH() {
        initComponents();
       init();
    }

    NguoiHocDAO dao = new NguoiHocDAO();
    int row = -1;

    public void init() {
        setLocation(170, 50);
        setResizable(false);

        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    public void insert() {
        try {
            if (Validate.checkTrungMaNH(txtMaNguoiHoc.getText()) == true) {
                mesageDiaLogHelper.showErrorDialog(this, "Mã chuyên đề đã tồn tại!", "Error!!!");
                return;
            } else if (Validate.checkEmty(this, txtMaNguoiHoc, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkSLKyTuNH(this, txtMaNguoiHoc, "Mã người học không hợp lệ!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtHoVaTen, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtNgaySinh, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkDate(this, txtNgaySinh, "Sai định dạng!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmty(this, txtDienThoai, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkPhoneNumber(txtDienThoai, "Sai định dạng!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkTrungSDTNH(txtDienThoai.getText()) == true) {
                mesageDiaLogHelper.showErrorDialog(this, "SDT này đã tồn tại!", "Error!!!");
                return;
            } else if (Validate.checkEmty(this, txtEmail, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkEmail(txtEmail, "Sai định dạng!!!", "Error!!!") == false) {
                return;
            } else if (Validate.checkTrungEmailNH(txtEmail.getText()) == true) {
                mesageDiaLogHelper.showErrorDialog(this, "Email này đã tồn tại!", "Error!!!");
                return;
            } else {
                NguoiHoc nh = getForm();
                dao.insert(nh);
                this.fillTable();
                mesageDiaLogHelper.showMessageDialog(this, "Thêm mới thành công!!", "Thông báo!!!");
                this.clearForm();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mesageDiaLogHelper.showErrorDialog(this, "Thêm mới thất bại!!", "Error!!!");
        }
    }

    public void update() {
        if (Validate.checkEmty(this, txtMaNguoiHoc, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtHoVaTen, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtNgaySinh, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkDate(this, txtNgaySinh, "Sai định dạng!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmty(this, txtDienThoai, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkPhoneNumber(txtDienThoai, "Sai định dạng!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkTrungSDTNH(txtDienThoai.getText()) == true) {
            mesageDiaLogHelper.showErrorDialog(this, "SDT này đã tồn tại!", "Error!!!");
            return;
        } else if (Validate.checkEmty(this, txtEmail, "Vui lòng điền đầy đủ thông tin!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkEmail(txtEmail, "Sai định dạng!!!", "Error!!!") == false) {
            return;
        } else if (Validate.checkTrungEmailNH(txtEmail.getText()) == true) {
            mesageDiaLogHelper.showErrorDialog(this, "Email này đã tồn tại!", "Error!!!");
            return;
        } else {
            NguoiHoc nh = getForm();
            try {
                dao.update(nh);
                this.fillTable();
                mesageDiaLogHelper.showMessageDialog(this, "Cập nhật thành công", "Thông báo!!");
            } catch (Exception e) {
                e.printStackTrace();
                mesageDiaLogHelper.showErrorDialog(this, "Cập nhật thất bại!!", "Error!!!");
            }
        }

    }

    public void delete() {
        if (!Auth.isManager()) {
            mesageDiaLogHelper.showErrorDialog(this, "Bạn không có quyền xoá người học này!!", "Error");
        } else {
            String manv = txtMaNguoiHoc.getText();
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
        this.clear();
        this.row = -1;
        this.updateStatus();
    }

    public void edit() {
        String manv = tblQLNH.getValueAt(this.row, 0).toString();
        NguoiHoc nv = dao.selectByID(manv);
        this.setForm(nv);
        tabs.setSelectedIndex(0);
        this.updateStatus();
    }

    public void clear() {
        txtMaNguoiHoc.setText("");
        txtHoVaTen.setText("");
        txtNgaySinh.setText("");
        txtEmail.setText("");
        txtDienThoai.setText("");
        txtGhiChu.setText("");
    }

    public void setForm(NguoiHoc nh) {
        txtMaNguoiHoc.setText(nh.getMaNH());
        txtHoVaTen.setText(nh.getHoTen());
        txtNgaySinh.setText(XDate.toString(nh.getNgaySinh(), "MM/dd/yyyy"));
        txtEmail.setText(nh.getEmail());
        txtDienThoai.setText(nh.getDienThoai());
        txtGhiChu.setText(nh.getGhiChu());
        if (nh.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
    }

    public NguoiHoc getForm() {
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtMaNguoiHoc.getText().toUpperCase());
        nh.setHoTen(txtHoVaTen.getText());
        nh.setGioiTinh(rdoNam.isSelected());
        nh.setNgaySinh(XDate.toDate(txtNgaySinh.getText(), "MM/dd/yyyy"));
        nh.setDienThoai(txtDienThoai.getText());
        nh.setEmail(txtEmail.getText());
        nh.setGhiChu(txtGhiChu.getText());
        return nh;
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
        if (this.row < tblQLNH.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    public void last() {
        this.row = tblQLNH.getRowCount() - 1;
        this.edit();
    }

    public void fillTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblQLNH.getModel();
        tblModel.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NguoiHoc> list = this.dao.selectByKeyword(keyword);
            for (NguoiHoc nguoiHoc : list) {
                tblModel.addRow(new Object[]{nguoiHoc.getMaNH(), nguoiHoc.getHoTen(), nguoiHoc.isGioiTinh() ? "Nam" : "Nữ", XDate.toString(nguoiHoc.getNgaySinh(), "MM/dd/yyyy"),
                    nguoiHoc.getDienThoai(), nguoiHoc.getEmail(), nguoiHoc.getMaNV(), XDate.toString(nguoiHoc.getNgayDK(), "MM/dd/yyyy")});
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
        boolean last = (this.row == tblQLNH.getSelectedRow() - 1);

        txtMaNguoiHoc.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirts.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !first);

    }

    private void timKiem() {
        fillTable();
        clearForm();
        row = -1;
        updateStatus();
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
        lblQLNH = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblNgayKhaiGiang = new javax.swing.JLabel();
        txtMaNguoiHoc = new javax.swing.JTextField();
        txtHoVaTen = new javax.swing.JTextField();
        lblThoiLuong = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        lblGhiChu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirts = new javax.swing.JButton();
        lblGioiTinh = new javax.swing.JLabel();
        lblDienThoai = new javax.swing.JLabel();
        txtDienThoai = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQLNH = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        lblQLNH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblQLNH.setForeground(new java.awt.Color(0, 0, 255));
        lblQLNH.setText("QUẢN LÝ NGƯỜI HỌC");

        lblNgayKhaiGiang.setText("Mã người học");

        lblThoiLuong.setText("Họ và tên");

        lblNgayTao.setText("Ngày sinh");

        lblGhiChu.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

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

        lblGioiTinh.setText("Giới tính");

        lblDienThoai.setText("Điện Thoại");

        lblEmail.setText("Địa chỉ email");

        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(btnFirts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
                    .addComponent(txtHoVaTen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGioiTinh)
                            .addComponent(lblGhiChu)
                            .addComponent(lblNgayKhaiGiang)
                            .addComponent(lblThoiLuong)
                            .addComponent(lblDienThoai)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail)
                                    .addComponent(lblNgayTao))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtEmail))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblNgayKhaiGiang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblThoiLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNgayTao)
                    .addComponent(lblGioiTinh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDienThoai)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        tabs.addTab("CẬP NHẬT", jPanel3);

        tblQLNH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY DK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLNHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblQLNH);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setText("Tìm");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirtsActionPerformed
        // TODO add your handling code here:
        firts();
    }//GEN-LAST:event_btnFirtsActionPerformed

    private void tblQLNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLNHMouseClicked
        // TODO add your handling code here:
        row = tblQLNH.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblQLNHMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirts;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDienThoai;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblNgayKhaiGiang;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblQLNH;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblQLNH;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoVaTen;
    private javax.swing.JTextField txtMaNguoiHoc;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
