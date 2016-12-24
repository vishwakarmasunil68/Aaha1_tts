package com.motivator.model;

/**
 * Created by sunil on 23-12-2016.
 */

public class UserHabitTable {

    String table_user_habits_id;
    String table_user_habits_user_id;
    String table_user_habits_user_name;
    String table_user_habits_habit_id;
    String table_user_habits_ritual_type;
    String table_user_habits_userhabit_time;
    String table_user_habits_is_habit_completed;
    String table_user_habits_habit_priority;
    String table_user_habits_habit_completed_on;
    String table_user_habits_reminder_next_desc;
    String table_user_habits_habit_added_on;

    public String getTable_user_habits_id() {
        return table_user_habits_id;
    }

    public void setTable_user_habits_id(String table_user_habits_id) {
        this.table_user_habits_id = table_user_habits_id;
    }

    public String getTable_user_habits_user_id() {
        return table_user_habits_user_id;
    }

    public void setTable_user_habits_user_id(String table_user_habits_user_id) {
        this.table_user_habits_user_id = table_user_habits_user_id;
    }

    public String getTable_user_habits_user_name() {
        return table_user_habits_user_name;
    }

    public void setTable_user_habits_user_name(String table_user_habits_user_name) {
        this.table_user_habits_user_name = table_user_habits_user_name;
    }

    public String getTable_user_habits_habit_id() {
        return table_user_habits_habit_id;
    }

    public void setTable_user_habits_habit_id(String table_user_habits_habit_id) {
        this.table_user_habits_habit_id = table_user_habits_habit_id;
    }

    public String getTable_user_habits_ritual_type() {
        return table_user_habits_ritual_type;
    }

    public void setTable_user_habits_ritual_type(String table_user_habits_ritual_type) {
        this.table_user_habits_ritual_type = table_user_habits_ritual_type;
    }

    public String getTable_user_habits_userhabit_time() {
        return table_user_habits_userhabit_time;
    }

    public void setTable_user_habits_userhabit_time(String table_user_habits_userhabit_time) {
        this.table_user_habits_userhabit_time = table_user_habits_userhabit_time;
    }

    public String getTable_user_habits_is_habit_completed() {
        return table_user_habits_is_habit_completed;
    }

    public void setTable_user_habits_is_habit_completed(String table_user_habits_is_habit_completed) {
        this.table_user_habits_is_habit_completed = table_user_habits_is_habit_completed;
    }

    public String getTable_user_habits_habit_priority() {
        return table_user_habits_habit_priority;
    }

    public void setTable_user_habits_habit_priority(String table_user_habits_habit_priority) {
        this.table_user_habits_habit_priority = table_user_habits_habit_priority;
    }

    public String getTable_user_habits_habit_completed_on() {
        return table_user_habits_habit_completed_on;
    }

    public void setTable_user_habits_habit_completed_on(String table_user_habits_habit_completed_on) {
        this.table_user_habits_habit_completed_on = table_user_habits_habit_completed_on;
    }

    public String getTable_user_habits_reminder_next_desc() {
        return table_user_habits_reminder_next_desc;
    }

    public void setTable_user_habits_reminder_next_desc(String table_user_habits_reminder_next_desc) {
        this.table_user_habits_reminder_next_desc = table_user_habits_reminder_next_desc;
    }

    public String getTable_user_habits_habit_added_on() {
        return table_user_habits_habit_added_on;
    }

    public void setTable_user_habits_habit_added_on(String table_user_habits_habit_added_on) {
        this.table_user_habits_habit_added_on = table_user_habits_habit_added_on;
    }

    @Override
    public String toString() {
        return "UserHabitTable{" +
                "table_user_habits_id='" + table_user_habits_id + '\'' +
                ", table_user_habits_user_id='" + table_user_habits_user_id + '\'' +
                ", table_user_habits_user_name='" + table_user_habits_user_name + '\'' +
                ", table_user_habits_habit_id='" + table_user_habits_habit_id + '\'' +
                ", table_user_habits_ritual_type='" + table_user_habits_ritual_type + '\'' +
                ", table_user_habits_userhabit_time='" + table_user_habits_userhabit_time + '\'' +
                ", table_user_habits_is_habit_completed='" + table_user_habits_is_habit_completed + '\'' +
                ", table_user_habits_habit_priority='" + table_user_habits_habit_priority + '\'' +
                ", table_user_habits_habit_completed_on='" + table_user_habits_habit_completed_on + '\'' +
                ", table_user_habits_reminder_next_desc='" + table_user_habits_reminder_next_desc + '\'' +
                ", table_user_habits_habit_added_on='" + table_user_habits_habit_added_on + '\'' +
                '}';
    }
}
