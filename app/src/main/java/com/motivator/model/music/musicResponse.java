package com.motivator.model.music;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 11-11-2016.
 */
public class musicResponse {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<result> list_result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<result> getList_result() {
        return list_result;
    }

    public void setList_result(List<result> list_result) {
        this.list_result = list_result;
    }

    @Override
    public String toString() {
        return "musicResponse{" +
                "success='" + success + '\'' +
                ", list_result=" + list_result.toString() +
                '}';
    }
}
