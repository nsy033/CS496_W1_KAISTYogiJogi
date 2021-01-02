package com.example.helloworld;

import android.graphics.drawable.Drawable;

public class ChildFragmentItem {

    private Drawable dep_map ; //학과 지도
    private String dep_num ; // 건물번호 또는 이름
    private String dep_king_phone;
    private String dep_king_email;
    private String dep_admin_phone;
    private String dep_admin_email;

    public void setDep_map(Drawable map) { this.dep_map = map ; }
    public void setDep_num(String dep_num) { this.dep_num = dep_num ; }
    public void setDep_king_phone(String dep_king_phone) { this.dep_king_phone = dep_king_phone ; }
    public void setDep_king_email(String dep_king_email) { this.dep_king_email = dep_king_email ; }
    public void setDep_admin_phone(String dep_admin_phone) { this.dep_admin_phone = dep_admin_phone ; }
    public void setDep_admin_email(String dep_admin_email) { this.dep_admin_email = dep_admin_email ; }

    public Drawable getDep_map() { return this.dep_map ; }
    public String getDep_num() { return this.dep_num ; }
    public String getDep_king_phone() { return this.dep_king_phone ; }
    public String getDep_king_email() { return this.dep_king_email ; }
    public String getDep_admin_phone() { return this.dep_admin_phone ; }
    public String getDep_admin_email() { return this.dep_admin_email ; }
}
