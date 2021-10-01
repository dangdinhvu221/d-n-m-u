/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

/**
 *
 * @author dangd
 */
public class regexType {

    public static String regexEmail() {
        return "\\w+@\\w+(\\.\\w+){1,2}";
    }

    public static String regexPhoneNumber() {
        return "(84|0[3|5|7|8|9])+([0-9]{8})";
    }
}
