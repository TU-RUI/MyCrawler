package com.model;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang3.StringUtils;

public class PicContent {
    //http://i2.pixiv.net/img-original/img/2016/12/01/00/01/02/60181109_p0.png
    //年
    private String year;
    //月
    private String month;
    //日
    private String day;
    //小时
    private String hour;
    //分钟
    private String min;
    //图片ID
    private int id;
    //是否一次投稿多张
    private boolean multi = false;
    //如果是多张,需要分片id
    private int pid;
    //图片数据
    private byte[] data;
    //图片类型
    private PicType type;
    
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isMulti() {
        return multi;
    }
    public void setMulti(boolean multi) {
        this.multi = multi;
    }
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public PicType getType() {
        return type;
    }
    public void setType(PicType type) {
        this.type = type;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getMin() {
        return min;
    }
    public void setMin(String min) {
        this.min = min;
    }
    
    public PicContent(String year, String month, String day, String hour, String min, int id, boolean multi, int pid,
            byte[] data, PicType type) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.id = id;
        this.multi = multi;
        this.pid = pid;
        this.data = data;
        this.type = type;
    }
    private boolean isValidate(){
        if(StringUtils.isBlank(year)||StringUtils.isBlank(month)||StringUtils.isBlank(day) || id == 0 || data == null){
            return false;
        }
        if(multi && pid == 0){
            return false;
        }
        return true;
    }
    
    public void save() throws Exception {
        if(!isValidate()){
            throw new Exception("isNotValidate");
        }
        String path = ".\\pic\\"+year+"\\"+month+"\\"+day;
        if(multi){
            path = path+"\\"+id + "\\"+ pid +"."+type;
        }else{
            path = path + "\\"+id+"."+type;
        }
        File targetFile = new File(path);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                FileChannel fo = fileOutputStream.getChannel();
        ) {
            File pf = targetFile.getParentFile();
            if (!pf.exists()) {
                pf.mkdirs();
            }
            fo.write(ByteBuffer.wrap(data));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    

}
