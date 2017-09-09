package com.map.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 10/20/2016.
 */

public class NearbysearchModel {
    @SerializedName("name")
    public String name;

    @SerializedName("icon")
    public String icon;

   /* @SerializedName("reference")
    public String reference;*/


    @SerializedName("vicinity")
    public String vicinity;

    @SerializedName("geometry")
    public Geometry geometry;

    public class Geometry
    {
        @SerializedName("location")
        public Location location;

        public class Location
        {
            @SerializedName("lat")
            public String lat = "23.024117";

            @SerializedName("lng")
            public String lng = "72.5674708";
        }

    }


    @SerializedName("photos")
    public ArrayList<PhotosModel> arrListPhotosModel;

    public  class  PhotosModel
    {
        @SerializedName("height")
        public String height;

        @SerializedName("width")
        public String width;

        @SerializedName("photo_reference")
        public String photo_reference;

    }
}
