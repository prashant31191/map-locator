package com.map.response;

import com.google.gson.annotations.SerializedName;
import com.map.model.NearbysearchModel;

import java.util.ArrayList;

/**
 * Created by Admin on 10/21/2016.
 */

public class NearbysearchListResponse extends CommonResponse {

    @SerializedName("next_page_token")
    public String next_page_token;

    @SerializedName("t")
    public String total;

    @SerializedName("results")
    public ArrayList<NearbysearchModel> arrListNearbysearchModel;
}






//api call




//response
