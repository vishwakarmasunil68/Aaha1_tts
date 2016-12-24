package com.motivator.model.music;

/**
 * Created by sunil on 11-11-2016.
 */
public class result {
    String video_id;
    String video_title;
    String video_destription;
    String video_name;
    String video_image;
    String video_date;
    String video_cat;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_destription() {
        return video_destription;
    }

    public void setVideo_destription(String video_destription) {
        this.video_destription = video_destription;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = video_image;
    }

    public String getVideo_date() {
        return video_date;
    }

    public void setVideo_date(String video_date) {
        this.video_date = video_date;
    }

    public String getVideo_cat() {
        return video_cat;
    }

    public void setVideo_cat(String video_cat) {
        this.video_cat = video_cat;
    }

    @Override
    public String toString() {
        return "result{" +
                "video_id='" + video_id + '\'' +
                ", video_title='" + video_title + '\'' +
                ", video_destription='" + video_destription + '\'' +
                ", video_name='" + video_name + '\'' +
                ", video_image='" + video_image + '\'' +
                ", video_date='" + video_date + '\'' +
                ", video_cat='" + video_cat + '\'' +
                '}';
    }
}
