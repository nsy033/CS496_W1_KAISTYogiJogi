package com.example.helloworld;

import android.graphics.drawable.Drawable;
import android.widget.GridView;

public class GridViewItem {
    private Drawable dep_iconDrawable ; //학과아이콘
    private String dep_str ; // 학과이름
    private String descrip ; //학과
    String position ; //건물 위치
    String tel ; // 전화번호
    String fax ; // 팩스
    int resId ; // 사진

    public GridViewItem() {}

    public String getDescrip() {
        return descrip;
    }
    public String getPosition() {
        return position;
    }
    public String getTel() {
        return tel;
    }
    public String getFax() {
        return fax;
    }
    public int getResId() {
        return resId;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setIcon(Drawable icon) { this.dep_iconDrawable = icon ; }
    public void setStr(String title) {
        this.dep_str = title ;
    }

    public Drawable getIcon() { return this.dep_iconDrawable ; }
    public String getStr() {
        return this.dep_str ;
    }

}
