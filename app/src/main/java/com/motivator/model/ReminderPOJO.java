package com.motivator.model;

/**
 * Created by sunil on 22-12-2016.
 */

public class ReminderPOJO {



    String rem_id;
    String reminder_id;
    String reminder_user_id;
    String reminder_user_name;
    String reminder_time;
    String reminder_ritual_type;
    String reminder_snooze_time;


    public String getRem_id() {
        return rem_id;
    }

    public void setRem_id(String rem_id) {
        this.rem_id = rem_id;
    }

    public String getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(String reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getReminder_user_id() {
        return reminder_user_id;
    }

    public void setReminder_user_id(String reminder_user_id) {
        this.reminder_user_id = reminder_user_id;
    }

    public String getReminder_user_name() {
        return reminder_user_name;
    }

    public void setReminder_user_name(String reminder_user_name) {
        this.reminder_user_name = reminder_user_name;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }

    public String getReminder_ritual_type() {
        return reminder_ritual_type;
    }

    public void setReminder_ritual_type(String reminder_ritual_type) {
        this.reminder_ritual_type = reminder_ritual_type;
    }

    public String getReminder_snooze_time() {
        return reminder_snooze_time;
    }

    public void setReminder_snooze_time(String reminder_snooze_time) {
        this.reminder_snooze_time = reminder_snooze_time;
    }

    @Override
    public String toString() {
        return "ReminderPOJO{" +
                "rem_id='" + rem_id + '\'' +
                ", reminder_id='" + reminder_id + '\'' +
                ", reminder_user_id='" + reminder_user_id + '\'' +
                ", reminder_user_name='" + reminder_user_name + '\'' +
                ", reminder_time='" + reminder_time + '\'' +
                ", reminder_ritual_type='" + reminder_ritual_type + '\'' +
                ", reminder_snooze_time='" + reminder_snooze_time + '\'' +
                '}';
    }
}
