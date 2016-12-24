package com.motivator.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 21-12-2016.
 */

public class HabitTimelinePOJO {
    @SerializedName("timeline_id")
    String timeline_id;
    @SerializedName("timeline_user_id")
    String timeline_user_id;
    @SerializedName("timeline_user_name")
    String timeline_user_name;
    @SerializedName("timeline_ritual_type")
    String timeline_ritual_type;
    @SerializedName("timeline_dateof_status")
    String timeline_date_ofstatus;
    @SerializedName("timelint_total_habits")
    String timeline_total_habits;
    @SerializedName("timelint_habitcompleted")
    String timeline_habitcompleted;

    public String getTimeline_id() {
        return timeline_id;
    }

    public void setTimeline_id(String timeline_id) {
        this.timeline_id = timeline_id;
    }

    public String getTimeline_user_id() {
        return timeline_user_id;
    }

    public void setTimeline_user_id(String timeline_user_id) {
        this.timeline_user_id = timeline_user_id;
    }

    public String getTimeline_user_name() {
        return timeline_user_name;
    }

    public void setTimeline_user_name(String timeline_user_name) {
        this.timeline_user_name = timeline_user_name;
    }

    public String getTimeline_ritual_type() {
        return timeline_ritual_type;
    }

    public void setTimeline_ritual_type(String timeline_ritual_type) {
        this.timeline_ritual_type = timeline_ritual_type;
    }

    public String getTimeline_date_ofstatus() {
        return timeline_date_ofstatus;
    }

    public void setTimeline_date_ofstatus(String timeline_date_ofstatus) {
        this.timeline_date_ofstatus = timeline_date_ofstatus;
    }

    public String getTimeline_total_habits() {
        return timeline_total_habits;
    }

    public void setTimeline_total_habits(String timeline_total_habits) {
        this.timeline_total_habits = timeline_total_habits;
    }

    public String getTimeline_habitcompleted() {
        return timeline_habitcompleted;
    }

    public void setTimeline_habitcompleted(String timeline_habitcompleted) {
        this.timeline_habitcompleted = timeline_habitcompleted;
    }

    @Override
    public String toString() {
        return "HabitTimelinePOJO{" +
                "timeline_id='" + timeline_id + '\'' +
                ", timeline_user_id='" + timeline_user_id + '\'' +
                ", timeline_user_name='" + timeline_user_name + '\'' +
                ", timeline_ritual_type='" + timeline_ritual_type + '\'' +
                ", timeline_date_ofstatus='" + timeline_date_ofstatus + '\'' +
                ", timeline_total_habits='" + timeline_total_habits + '\'' +
                ", timeline_habitcompleted='" + timeline_habitcompleted + '\'' +
                '}';
    }
}
