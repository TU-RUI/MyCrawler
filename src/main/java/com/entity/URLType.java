package com.entity;

public enum URLType {
    SINGLE_PIC_INFO(0), SINGLE_ORIGINAL_PIC(1),MULTI_PIC_INFO(2),MULTI_ORIGINAL_PIC(3),AUTHOR_INFO_PAGE(4),AUTHOR_WORKS_PAGE(5);
    private int value;
    
    URLType(int val){
        this.value = val;
    }
    
    public int val(){
        return this.value;
    }
}
