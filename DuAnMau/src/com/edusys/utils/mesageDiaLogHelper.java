/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author dangd
 */
public class mesageDiaLogHelper {
    public static void showMessageDialog(Component component, String message, String titel) {
        JOptionPane.showMessageDialog(component, message, titel, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorDialog(Component component, String message, String titel) {
        JOptionPane.showMessageDialog(component, message, titel, JOptionPane.ERROR_MESSAGE);
    }

    public static int showComfirmDialog(Component component, String message, String titel) {
        int chosse = JOptionPane.showConfirmDialog(component, message, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return chosse;
    }
    
    public static void showInputDialog(Component component, String message, String titel){
        JOptionPane.showInputDialog(component, message, JOptionPane.INFORMATION_MESSAGE);
    }
}
