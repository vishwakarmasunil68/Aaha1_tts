package com.motivator.model;

/**
 * Created by sunil on 22-12-2016.
 */

public class JourneyPOJO {

    String j_id;
    String j_user_id;
    String j_user_name;
    String j_journey_name;
    String j_total_events;
    String j_total_events_achived;
    String j_status_step1;
    String j_status_step2;
    String j_status_step3;
    String j_status_step4;
    String j_status_step5;

    @Override
    public String toString() {
        return "JourneyPOJO{" +
                "j_id='" + j_id + '\'' +
                ", j_user_id='" + j_user_id + '\'' +
                ", j_user_name='" + j_user_name + '\'' +
                ", j_journey_name='" + j_journey_name + '\'' +
                ", j_total_events='" + j_total_events + '\'' +
                ", j_total_events_achived='" + j_total_events_achived + '\'' +
                ", j_status_step1='" + j_status_step1 + '\'' +
                ", j_status_step2='" + j_status_step2 + '\'' +
                ", j_status_step3='" + j_status_step3 + '\'' +
                ", j_status_step4='" + j_status_step4 + '\'' +
                ", j_status_step5='" + j_status_step5 + '\'' +
                '}';
    }

    public String getJ_id() {
        return j_id;
    }

    public void setJ_id(String j_id) {
        this.j_id = j_id;
    }

    public String getJ_user_id() {
        return j_user_id;
    }

    public void setJ_user_id(String j_user_id) {
        this.j_user_id = j_user_id;
    }

    public String getJ_user_name() {
        return j_user_name;
    }

    public void setJ_user_name(String j_user_name) {
        this.j_user_name = j_user_name;
    }

    public String getJ_journey_name() {
        return j_journey_name;
    }

    public void setJ_journey_name(String j_journey_name) {
        this.j_journey_name = j_journey_name;
    }

    public String getJ_total_events() {
        return j_total_events;
    }

    public void setJ_total_events(String j_total_events) {
        this.j_total_events = j_total_events;
    }

    public String getJ_total_events_achived() {
        return j_total_events_achived;
    }

    public void setJ_total_events_achived(String j_total_events_achived) {
        this.j_total_events_achived = j_total_events_achived;
    }

    public String getJ_status_step1() {
        return j_status_step1;
    }

    public void setJ_status_step1(String j_status_step1) {
        this.j_status_step1 = j_status_step1;
    }

    public String getJ_status_step2() {
        return j_status_step2;
    }

    public void setJ_status_step2(String j_status_step2) {
        this.j_status_step2 = j_status_step2;
    }

    public String getJ_status_step3() {
        return j_status_step3;
    }

    public void setJ_status_step3(String j_status_step3) {
        this.j_status_step3 = j_status_step3;
    }

    public String getJ_status_step4() {
        return j_status_step4;
    }

    public void setJ_status_step4(String j_status_step4) {
        this.j_status_step4 = j_status_step4;
    }

    public String getJ_status_step5() {
        return j_status_step5;
    }

    public void setJ_status_step5(String j_status_step5) {
        this.j_status_step5 = j_status_step5;
    }
}
