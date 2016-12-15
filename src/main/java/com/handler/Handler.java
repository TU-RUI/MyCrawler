package com.handler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Request;
import com.entity.Response;
import com.entity.URLType;
import com.model.PicContent;
import com.model.PicInfoContent;
import com.model.UserInfoContent;

public class Handler {
    private static Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    //请求预处理
	//判断去重
    public void before(Request request){
        
    }
    
    //响应处理
    public void after(Response response){
        Request request = response.getRequest();
        URLType urlType = request.getUrlType();
        if(urlType.val() == URLType.AUTHOR_INFO_PAGE.val()){//作者基本信息页
            String content = response.getContent();
            if(!StringUtils.isBlank(content)){
                Document doc = Jsoup.parse(content);
                UserInfoContent userinfo = new UserInfoContent();
                //TODO 解析个人信息
            }
        }else if(urlType.val() == URLType.AUTHOR_WORKS_PAGE.val()){//作者作品列表页
            String content = response.getContent();
            if(!StringUtils.isBlank(content)){
                Document doc = Jsoup.parse(content);
                //TODO 解析作品列表,添加request进入queue
                
            }
        }else if(urlType.val() == URLType.SINGLE_ORIGINAL_PIC.val()){//单张原图链接
            if(response.getData() != null){
                PicContent picContent = new PicContent();
                picContent.setData(response.getData());
                //TODO 保存图片
//                picContent.setDay();
//                piContent.setId();
//                picContent.setHour();
//                picContent.setMin();
//                picContent.setMonth();
//                picContent.setYear();
                picContent.setMulti(false);
//                picContent.setType(PicType.);
                try {
                    picContent.save();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOGGER.error(e.getMessage());
                }
            }
        }else if(urlType.val() == URLType.MULTI_ORIGINAL_PIC.val()){//作品集单图链接
            if(response.getData() != null){
                PicContent picContent = new PicContent();
                picContent.setData(response.getData());
                //TODO 保存图片
//                picContent.setDay();
//                piContent.setId();
//                picContent.setHour();
//                picContent.setMin();
//                picContent.setMonth();
//                picContent.setYear();
                picContent.setMulti(true);
//                picContent.setPid();
//                picContent.setType(PicType.);
                try {
                    picContent.save();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOGGER.error(e.getMessage());
                }
            }
        }else if(urlType.val() == URLType.MULTI_PIC_INFO.val()){//作品集信息链接
            String content = response.getContent();
            if(!StringUtils.isBlank(content)){
                PicInfoContent pic = new PicInfoContent();
                Document doc = Jsoup.parse(content);
                //TODO 解析保存图片信息
                
                
            }
        }else if(urlType.val() == URLType.SINGLE_PIC_INFO.val()){//单张作品信息链接
            String content = response.getContent();
            if(!StringUtils.isBlank(content)){
                PicInfoContent pic = new PicInfoContent();
                Document doc = Jsoup.parse(content);
                //TODO 解析保存图片信息
                
                
            }
        }
    }
}
