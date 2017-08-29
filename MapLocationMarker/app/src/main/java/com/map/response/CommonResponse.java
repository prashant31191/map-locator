package com.map.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 8/5/2016.
 */

public class CommonResponse
{
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }
}
