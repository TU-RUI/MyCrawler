package com.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
@Entity(value="users")
public class UserInfoContent {
    @Id
    private ObjectId _id;
	//P站id
	private int userID;
	//昵称
	private String name;
	//个人主页
	private String homePage;
	//职业
	private String profession;
	//自我介绍
	private String introduce;
	//性别
	private String sex;
	//年龄
	private int age;
	//地址
	private String address;
	//投稿
	@Embedded
	private List<Integer> pictures;
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHomePage() {
        return homePage;
    }
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Integer> getPictures() {
        return pictures;
    }
    public void setPictures(List<Integer> pictures) {
        this.pictures = pictures;
    }
    public UserInfoContent(int userID, String name, String homePage, String profession, String introduce, String sex,
            int age, String address, List<Integer> pictures) {
        super();
        this.userID = userID;
        this.name = name;
        this.homePage = homePage;
        this.profession = profession;
        this.introduce = introduce;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.pictures = pictures;
    }
	
}
