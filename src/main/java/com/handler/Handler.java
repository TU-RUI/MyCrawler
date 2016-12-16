package com.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crawler.Crawler;
import com.dao.MongoUtils;
import com.entity.Request;
import com.entity.Response;
import com.entity.URLType;
import com.http.HttpMethod;
import com.model.PicContent;
import com.model.PicInfoContent;
import com.model.UserInfoContent;
import com.queues.RedisQueue;

public class Handler {
    private static Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    private static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static RedisQueue queue = Crawler.getInstance().getQueue();
    private static MongoUtils mongoUtils = MongoUtils.getInstance();
    // 请求预处理
    // 判断去重
    public void before(Request request) {
        
    }

    // 响应处理
    public void after(Response response) {
        Request request = response.getRequest();
        URLType urlType = request.getUrlType();
        if (urlType.val() == URLType.AUTHOR_INFO_PAGE.val()) {// 作者基本信息页,http://www.pixiv.net/member.php?id=1249098
            String content = response.getContent();
            if (!StringUtils.isBlank(content)) {
                Document doc = Jsoup.parse(content);
                UserInfoContent userinfo = new UserInfoContent();
                // TODO 解析个人信息
                
                mongoUtils.addUserEntity(userinfo);
                // 添加作者作品列表页,仅第一页
                Request newRequest = new Request();
                // newRequest.setUrl();
                newRequest.setUrlType(URLType.PIC_INFO_PAGE);
                newRequest.setReferer(request.getUrl());
                newRequest.setMaxReqCount(3);
                newRequest.setHttpMethod(HttpMethod.GET);
                queue.push(newRequest);
            }
        } else if (urlType.val() == URLType.AUTHOR_WORKS_PAGE.val()) {// 作者作品列表页,http://www.pixiv.net/member_illust.php?id=1249098
            String content = response.getContent();
            if (!StringUtils.isBlank(content)) {
                Document doc = Jsoup.parse(content);
                // TODO 解析作品列表,添加request进入queue

                // 如果有多页,添加下一页
                Request newRequest = new Request();
                // newRequest.setUrl();
                newRequest.setUrlType(URLType.AUTHOR_WORKS_PAGE);
                newRequest.setReferer(request.getUrl());
                newRequest.setMaxReqCount(3);
                newRequest.setHttpMethod(HttpMethod.GET);
                queue.push(newRequest);
                // 添加每个作品信息页
                Request newRequest1 = new Request();
                // newRequest.setUrl();
                newRequest1.setUrlType(URLType.PIC_INFO_PAGE);
                newRequest1.setReferer(request.getUrl());
                newRequest1.setMaxReqCount(3);
                newRequest1.setHttpMethod(HttpMethod.GET);
                queue.push(newRequest1);
            }
        } else if (urlType.val() == URLType.PIC_INFO_PAGE.val()) {// 图片或图片集信息页
            //http://www.pixiv.net/member_illust.php?mode=medium&illust_id=59656701
            //http://www.pixiv.net/member_illust.php?mode=medium&illust_id=59481775
            String content = response.getContent();
            if (!StringUtils.isBlank(content)) {
                Document doc = Jsoup.parse(content);
                PicInfoContent pic = new PicInfoContent();
                // pic.setAuthorID();
                // pic.setCreateDate();
                pic.setFetchDate(dateFormate.format(new Date()));
                // pic.setLabels();
                // pic.setMulti();
                // pic.setPicHeight();
                // pic.setPicWidth();
                // pic.setPicID();
                // pic.setPicTitle();
                // pic.setRateNum();
                // pic.setScore();
                // pic.setViewNum();
                // pic.setSummary();
                // pic.setType();
                // pic.setUrl();
                
                mongoUtils.addPicEntity(pic);
                // 添加图片或作品集链接
                Request newRequest = new Request();
                // newRequest.setUrl();
                newRequest.setUrlType(URLType.SINGLE_ORIGINAL_PIC);
//                newRequest.setUrlType(URLType.MULTI_PIC_SET);
                newRequest.setReferer(request.getUrl());
                newRequest.setMaxReqCount(3);
                newRequest.setHttpMethod(HttpMethod.GET);
                queue.push(newRequest);
            }
        } else if (urlType.val() == URLType.MULTI_PIC_SET.val()) {// 包含多张图片的地址的作品集
            //http://www.pixiv.net/member_illust.php?mode=manga&illust_id=59481775
            String content = response.getContent();
            if (!StringUtils.isBlank(content)) {
                Document doc = Jsoup.parse(content);
                // TODO 解析出每张图片的连接
                
                // 添加每张图片链接
                Request newRequest = new Request();
                // newRequest.setUrl();
                newRequest.setUrlType(URLType.PIC_INFO_PAGE);
                newRequest.setReferer(request.getUrl());
                newRequest.setMaxReqCount(3);
                newRequest.setHttpMethod(HttpMethod.GET);
                queue.push(newRequest);
            }
        } else if (urlType.val() == URLType.SINGLE_ORIGINAL_PIC.val()) {// 单张图片链接
            //单图,http://i3.pixiv.net/img-original/img/2016/11/25/00/02/50/60095034_p0.png
            //http://i1.pixiv.net/img-original/img/2016/11/02/00/03/14/59756372_p0.png
            //http://i3.pixiv.net/img-original/img/2016/12/11/00/01/09/60322162_p0.jpg
            //多图,http://i4.pixiv.net/c/1200x1200/img-master/img/2016/10/15/19/44/40/59481775_p1_master1200.jpg
            byte[] data = response.getData();
            if (data != null) {
                // TODO 解析保存图片
                PicContent picContent = new PicContent();
                picContent.setData(response.getData());
                // picContent.setDay();
                // piContent.setId();
                // picContent.setHour();
                // picContent.setMin();
                // picContent.setMonth();
                // picContent.setYear();
                // picContent.setMulti();
                // picContent.setPid();
                // picContent.setType(PicType.);
                try {
                    picContent.save();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOGGER.error("保存图片出现异常:{}", e.getMessage());
                }

            }
        }
    }
}
