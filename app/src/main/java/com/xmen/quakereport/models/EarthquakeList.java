package com.xmen.quakereport.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EarthquakeList {

    @SerializedName("features")
    @Expose
    private ArrayList<Feature> features = new ArrayList<>();

    /**
     * @return The contacts
     */
    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public class Feature {

        @SerializedName("properties")
        @Expose
        private Property properties;

        public Property getProperties() { return  properties; }

        public class Property {

            @SerializedName("mag")
            @Expose
            private Double mag;

            @SerializedName("place")
            @Expose
            private String place;

            @SerializedName("time")
            @Expose
            private Long time;

            public Double getMag() { return mag; }
            public String getPlace() { return place; }
            public Long getTime() { return time; }

        }



    }

}
