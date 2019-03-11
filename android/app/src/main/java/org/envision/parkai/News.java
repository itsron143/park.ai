package org.envision.parkai;

/**
 * Created by root on 10/3/19.
 */

public class News {

    private String title;
    private String desc;
    private String image;
    private String url;
    private String time;

    public News(String title, String desc, String image, String url,String time) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.url = url;
        this.time=time;
    }

    public News(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
