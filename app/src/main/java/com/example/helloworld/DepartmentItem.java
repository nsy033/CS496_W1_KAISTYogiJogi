package com.example.helloworld;

public class DepartmentItem {
    private String dep_name;
    private String dep_num ; // 건물번호 또는 이름
    private String dep_king_phone;
    private String dep_king_email;
    private String dep_admin_phone;
    private String dep_admin_email;
    private String dep_url;
    private double dep_latitude;
    private double dep_longitude;


    public void setDep_name(String dep_name) { this.dep_name = dep_name ; }
    public void setDep_num(String dep_num) { this.dep_num = dep_num ; }
    public void setDep_king_phone(String dep_king_phone) { this.dep_king_phone = dep_king_phone ; }
    public void setDep_king_email(String dep_king_email) { this.dep_king_email = dep_king_email ; }
    public void setDep_admin_phone(String dep_admin_phone) { this.dep_admin_phone = dep_admin_phone ; }
    public void setDep_admin_email(String dep_admin_email) { this.dep_admin_email = dep_admin_email ; }
    public void setDep_url(String dep_url) { this.dep_url = dep_url ; }
    public void setDep_latitude(double dep_latitude) { this.dep_latitude = dep_latitude ; }
    public void setDep_longitude(double dep_longitude) { this.dep_longitude = dep_longitude ; }

    public String getDep_name() { return this.dep_name; }
    public String getDep_num() { return this.dep_num ; }
    public String getDep_king_phone() { return this.dep_king_phone ; }
    public String getDep_king_email() { return this.dep_king_email ; }
    public String getDep_admin_phone() { return this.dep_admin_phone ; }
    public String getDep_admin_email() { return this.dep_admin_email ; }
    public String getDep_url() { return this.dep_url; }
    public double getDep_latitude() { return this.dep_latitude; }
    public double getDep_longitude() { return this.dep_longitude; }
}