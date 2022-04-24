package edu.neu.madcourse.paws;

public class LocationInfo {
    public String user_name;
    public String longitude;
    public String latitude;

    public LocationInfo(){

    }

    public LocationInfo(String user_name, String longitude, String latitude){
        this.user_name = user_name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getUser_name() {
        return user_name;
    }
}
