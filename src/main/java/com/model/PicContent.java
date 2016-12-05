package com.model;

import java.util.List;

public class PicContent {
	//创作时间
	private String date;
	//图片标题
	private String picTitle;
	//类型:illust插画,manga漫画,ugoira动图
	private String type;
	//宽
	private String picWidth;
	//高
	private String picHeight;
	//图片ID
	private int picID;
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
	private List<String> labels;
	
	//图片P站url
	private String url;
	
	
}
