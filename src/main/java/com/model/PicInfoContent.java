package com.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="pictures")
public class PicInfoContent {
    @Id
    private ObjectId _id;
	//创作时间
	private String createDate;
	//抓取时间
	private String fetchDate;
    //图片标题
	private String picTitle;
	//图片ID
	private int picID;
	//类型:illust插画,manga漫画,ugoira动图
	private String type;
	//宽
	private String picWidth;
	//高
	private String picHeight;
	//浏览量
	private int viewNum;
	//评价次数
	private int rateNum;
	//总分
	private int score;
	//作者ID
	private int authorID;
	//是否多张图片
	private int multi;
	//图片简介
	private String summary;
	
	//图片标签
	@Embedded
	private List<String> labels;
	
	//图片P站url
	private String url;

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(String picWidth) {
        this.picWidth = picWidth;
    }

    public String getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(String picHeight) {
        this.picHeight = picHeight;
    }

    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(String fetchDate) {
        this.fetchDate = fetchDate;
    }

    public PicInfoContent(ObjectId _id, String createDate, String fetchDate, String picTitle, String type, String picWidth,
            String picHeight, int picID, int viewNum, int rateNum, int score, int authorID, int multi, String summary,
            List<String> labels, String url) {
        super();
        this._id = _id;
        this.createDate = createDate;
        this.fetchDate = fetchDate;
        this.picTitle = picTitle;
        this.type = type;
        this.picWidth = picWidth;
        this.picHeight = picHeight;
        this.picID = picID;
        this.viewNum = viewNum;
        this.rateNum = rateNum;
        this.score = score;
        this.authorID = authorID;
        this.multi = multi;
        this.summary = summary;
        this.labels = labels;
        this.url = url;
    }

    public PicInfoContent() {
        // TODO Auto-generated constructor stub
    }

	
	
}
