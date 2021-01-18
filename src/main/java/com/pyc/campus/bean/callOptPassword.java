package com.pyc.campus.bean;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file callOptPassword
 * @pack com.pyc.campus.bean
 * @date 2021/1/18
 * @time 11:05
 * @E-mail 2923616405@qq.com
 **/


public class callOptPassword {
    public static void main(String[] args) {
        try{
            OptPassword opt = new OptPassword();
            String p1 = "pycycy753";
            String ep1 = opt.encrypt(p1);
            System.out.println(p1 + ":" +ep1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
