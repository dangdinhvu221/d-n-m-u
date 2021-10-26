/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.dao.HocVienDAO;
import com.edusys.dao.NguoiHocDAO;
import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.ChuyenDe;
import com.edusys.entity.HocVien;
import com.edusys.entity.NguoiHoc;
import com.edusys.entity.NhanVien;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author dangd
 */
public class Validate {

    public static boolean checkEmty(Component component, JTextField txtField, String messenger, String titel) {
        if (txtField.getText().isEmpty()) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            txtField.setBorder(new LineBorder(Color.RED));
            return false;
        }
        txtField.setBorder(new LineBorder(Color.GREEN));
        return true;
    }

    public static boolean checkPasswordEmty(Component component, JPasswordField pass, String messenger, String titel) {
        if (pass.getText().isEmpty()) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            pass.setBorder(new LineBorder(Color.RED));
            return false;
        }
        pass.setBorder(new LineBorder(Color.GREEN));
        return true;
    }

    public static boolean checkSLKyTuCD(Component component, JTextField field, String messenger, String titel) {
        if (field.getText().length() > 5) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            field.setBorder(new LineBorder(Color.RED));
            return false;
        }
        field.setBorder(new LineBorder(Color.GREEN));
        return true;
    }

    public static boolean checkSLKyTuNH(Component component, JTextField field, String messenger, String titel) {
        if (field.getText().length() > 7) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            field.setBorder(new LineBorder(Color.RED));
            return false;
        }
        field.setBorder(new LineBorder(Color.GREEN));
        return true;
    }

    public static boolean checkEmtylbl(Component component, JLabel lbl, String messenger, String titel) {
        if (lbl.getText() == null) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            lbl.setBorder(new LineBorder(Color.RED));
            return false;
        } else {
            lbl.setBorder(new LineBorder(Color.GREEN));
            return true;
        }
    }

    public static boolean checkNumber(Component component, JTextField field, String messenger, String titel) {
        boolean check = true;
        try {
            double poin = Double.parseDouble(field.getText());
            if (poin < 0) {
                mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
                field.setBorder(new LineBorder(Color.RED));
                check = false;
            } else {
                field.setBorder(new LineBorder(Color.GREEN));
                check = true;
            }
        } catch (Exception e) {
            mesageDiaLogHelper.showErrorDialog(field, "Sai định dạng!!!", "Error");
            field.setBorder(new LineBorder(Color.RED));
            check = false;
        }
        return check;
    }
    public static boolean checkEmail(JTextField field, String messenger, String titel) {
        boolean flag = true;

        Pattern pattern = Pattern.compile(regexType.regexEmail());
        Matcher matcher = pattern.matcher(field.getText());
        if (!matcher.find()) {
            mesageDiaLogHelper.showErrorDialog(field, messenger, titel);
            field.setBorder(new LineBorder(Color.RED));
            flag = false;
        } else {
            field.setBorder(new LineBorder(Color.GREEN));
            flag = true;
        }
        return flag;
    }

    public static boolean checkDate(Component component, JTextField txtString, String messenger, String titel) {
        boolean flag = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String fmatDate = sdf.format(sdf.parse(txtString.getText()));
            if (fmatDate.equals(txtString.getText())) {
                flag = true;
                txtString.setBorder(new LineBorder(Color.GREEN));
            } else {
                mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
                txtString.setBorder(new LineBorder(Color.RED));
                flag = false;
            }
        } catch (Exception e) {
            mesageDiaLogHelper.showErrorDialog(component, messenger, titel);
            txtString.setBorder(new LineBorder(Color.RED));
            flag = false;
        }
        return flag;
    }

    public static boolean checkPhoneNumber(JTextField field, String messenger, String titel) {
        Pattern pattern = Pattern.compile(regexType.regexPhoneNumber());
        Matcher matcher = pattern.matcher(field.getText());
        boolean flag = true;

        if (!matcher.find()) {
            mesageDiaLogHelper.showErrorDialog(field, messenger, titel);
            field.setBorder(new LineBorder(Color.RED));
            flag = false;
        } else {
            field.setBorder(new LineBorder(Color.GREEN));
            flag = true;
        }
        return flag;
    }

    public static boolean checkTrungMaNV(String codeID) {
        NhanVienDAO dao = new NhanVienDAO();
        NhanVien nv = dao.selectByID(codeID);
        if (nv != null) {
            if (nv.getMaNV().equalsIgnoreCase(codeID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTrungMaHV(int codeID) {
        HocVienDAO dao = new HocVienDAO();
        HocVien hv = dao.selectByID(codeID);
        int mahv = hv.getMaHV();
        if (hv != null) {
            if (mahv == codeID) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTrungSDTNH(String phoneNumber) {
        NguoiHocDAO dao = new NguoiHocDAO();
        List<NguoiHoc> list = dao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDienThoai().trim().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTrungEmailNH(String email) {
        NguoiHocDAO dao = new NguoiHocDAO();
        List<NguoiHoc> list = dao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmail().trim().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTrungMaCD(String codeID) {
        ChuyenDeDAO dao = new ChuyenDeDAO();
        ChuyenDe cd = dao.selectByID(codeID);
        if (cd != null) {
            if (cd.getMaCD().trim().equalsIgnoreCase(codeID)) {
                return true;
            }

        }
        return false;
    }

    public static boolean checkTrungMaNH(String codeID) throws Exception {
        NguoiHocDAO dao = new NguoiHocDAO();
        NguoiHoc nh = dao.selectByID(codeID);
        if (nh != null) {
            if (nh.getMaNH().trim().equalsIgnoreCase(codeID)) {
                return true;
            }
        }
        return false;
    }
}
