package com.motivator.model;

/**
 * Created by sunil on 22-12-2016.
 */

public class ReminderDescPOJO {
    String rem_desc_id;
    String rem_desc_user_id;
    String rem_desc_user_name;
    String rem_desc_time;
    String rem_desc_day;
    String rem_desc_on_off;
    String rem_desc_stamp;
    String rem_desc_rem_id;

    @Override
    public String toString() {
        return "ReminderDescPOJO{" +
                "rem_desc_id='" + rem_desc_id + '\'' +
                ", rem_desc_user_id='" + rem_desc_user_id + '\'' +
                ", rem_desc_user_name='" + rem_desc_user_name + '\'' +
                ", rem_desc_time='" + rem_desc_time + '\'' +
                ", rem_desc_day='" + rem_desc_day + '\'' +
                ", rem_desc_on_off='" + rem_desc_on_off + '\'' +
                ", rem_desc_stamp='" + rem_desc_stamp + '\'' +
                ", rem_desc_rem_id='" + rem_desc_rem_id + '\'' +
                '}';
    }

    public String getRem_desc_id() {
        return rem_desc_id;
    }

    public void setRem_desc_id(String rem_desc_id) {
        this.rem_desc_id = rem_desc_id;
    }

    public String getRem_desc_user_id() {
        return rem_desc_user_id;
    }

    public void setRem_desc_user_id(String rem_desc_user_id) {
        this.rem_desc_user_id = rem_desc_user_id;
    }

    public String getRem_desc_user_name() {
        return rem_desc_user_name;
    }

    public void setRem_desc_user_name(String rem_desc_user_name) {
        this.rem_desc_user_name = rem_desc_user_name;
    }

    public String getRem_desc_time() {
        return rem_desc_time;
    }

    public void setRem_desc_time(String rem_desc_time) {
        this.rem_desc_time = rem_desc_time;
    }

    public String getRem_desc_day() {
        return rem_desc_day;
    }

    public void setRem_desc_day(String rem_desc_day) {
        this.rem_desc_day = rem_desc_day;
    }

    public String getRem_desc_on_off() {
        return rem_desc_on_off;
    }

    public void setRem_desc_on_off(String rem_desc_on_off) {
        this.rem_desc_on_off = rem_desc_on_off;
    }

    public String getRem_desc_stamp() {
        return rem_desc_stamp;
    }

    public void setRem_desc_stamp(String rem_desc_stamp) {
        this.rem_desc_stamp = rem_desc_stamp;
    }

    public String getRem_desc_rem_id() {
        return rem_desc_rem_id;
    }

    public void setRem_desc_rem_id(String rem_desc_rem_id) {
        this.rem_desc_rem_id = rem_desc_rem_id;
    }
}
