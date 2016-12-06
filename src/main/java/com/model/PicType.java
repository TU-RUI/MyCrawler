package com.model;

public enum PicType {
    PNG("png"),JPEG("post"),GIF("gif"),BMP("bmp");
    private String val;
    PicType(String val){
        this.val = val;
    }
    public String val(){
        return this.val;
    }
}
