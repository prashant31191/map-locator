package com.map.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Admin on 10/21/2016.
 */
public class NearbyCustomModel
{

    /**
     * The Name.
     */
    public String name;
    /**
     * The Vicinity.
     */
    public String vicinity;
    /**
     * The Icon.
     */
    public String icon;


    /**
     * The Width.
     */
    public String width;
    /**
     * The Height.
     */
    public String height;
    /**
     * The Photo reference.
     */
    public String photo_reference;


    public LatLng latLng;


    /**
     * Instantiates a new Nearby custom model.
     *
     * @param name            the name
     * @param vicinity        the vicinity
     * @param icon            the icon
     * @param width           the width
     * @param height          the height
     * @param photo_reference the photo reference
     */
    public NearbyCustomModel(String name, String vicinity, String icon, String width, String height, String photo_reference, LatLng latLng)
    {
        this.name = name;
        this.vicinity = vicinity;
        this.icon = icon;
        this.width = width;
        this.height = height;
        this.photo_reference = photo_reference;
        this.latLng = latLng;


    }

}
