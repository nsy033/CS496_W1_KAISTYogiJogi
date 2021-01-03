package com.example.myapplication;

import android.os.Parcelable;

import java.io.Serializable;

public class ContactItem implements Serializable {
    private String user_phNumber, user_name;
    private String photo_id, person_id;
    private String mail, address;
    private int id;

    public ContactItem(){}

    public String getPhoto_id(){return photo_id;}
    public String getPerson_id(){return person_id;}
    public void setPhoto_id(String id){this.photo_id = id;}
    public void setPerson_id(String id){this.person_id = id;}

    public String getMail(){return mail;}
    public void setMail(String mail){this.mail = mail;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getUser_phNumber(){return user_phNumber;}
    public String getUser_name(){return user_name;}
    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    public void setUser_phNumber(String string){this.user_phNumber = string;}
    public void setUser_name(String string){this.user_name = string; }

    public String toString(){
        return this.user_phNumber;
    }
    public int hashCode(){
        return getPhNumberChanged().hashCode();
    }
    public String getPhNumberChanged(){
        return user_phNumber.replace("-","");
    }
    public boolean equals(Object o){
        if(o instanceof ContactItem) return getPhNumberChanged().equals(((ContactItem) o).getPhNumberChanged());
        return false;
    }
}