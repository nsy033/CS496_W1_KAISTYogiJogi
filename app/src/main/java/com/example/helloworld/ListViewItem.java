package com.example.helloworld;

import android.graphics.Bitmap;

public class ListViewItem {
    private Bitmap iconDrawable;
    private String titleStr=null;
    private String descStr=null;
    private String mail=null;
    private String address=null;

    public void setIcon(Bitmap icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setMail(String mail) {this.mail = mail;}
    public void setAddress(String address) {this.address = address;}

    public Bitmap getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getMail() { return this.mail;}
    public String getAddress() { return this.address;}
}