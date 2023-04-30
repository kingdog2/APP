package com.example.check_excel;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Blob;

///為了傳遞此物件要implements
public class MyListData implements Serializable {

    private String name, studentID, introduce;
    private Bitmap imageID;
    private int  ID;

    public MyListData(String name, String studentID, String introduce, Bitmap imageID, int ID){
        this.name = name;
        this.studentID = studentID;
        this.introduce = introduce;
        this.imageID = imageID;
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudentID(String studentID) { this.studentID = studentID;}
    public void setIntroduce(String introduce) { this.introduce = introduce;}
    public void setImageId(Bitmap imageId) {
        this.imageID = imageId;
    }
    public void setID(int ID) { this.ID = ID; }

    public String getName() {
        return name;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getIntroduce() {
        return introduce;
    }
    public Bitmap getImageId() {
        return imageID;
    }
    public int getID() { return ID; }
}
