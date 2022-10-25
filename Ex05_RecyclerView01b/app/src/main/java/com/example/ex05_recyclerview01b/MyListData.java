package com.example.ex05_recyclerview01b;

public class MyListData {

    private String description;
    private int  imageId;

    public MyListData(String description, int imageId){
        this.description = description;
        this.imageId = imageId;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }



    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }
}
