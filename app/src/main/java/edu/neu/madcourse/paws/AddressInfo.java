package edu.neu.madcourse.paws;

public class AddressInfo {
    public String user_email;
    public String city;
    public String state;
    public String country;

    public AddressInfo(){

    }

    public AddressInfo(String user_email, String city, String state, String country){
        this.user_email = user_email;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getState() {
        return state;
    }
}

