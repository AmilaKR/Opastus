package com.akr.amila.opastus;

public class Robot {

    String name;
    long pass;
    double lon = 6.0;
    double lat = -640.0;
    int bat;
    public Robot(){

    }

    public Robot(String name, long pass, double lon, double lat, int bat) {
        this.name = name;
        this.pass = pass;
        this.lon = lon;
        this.lat = lat;
        this.bat = bat;
    }

    public double getLon() {
        return lon;
    }

    public long getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public int getBat() {
        return bat;
    }
}
