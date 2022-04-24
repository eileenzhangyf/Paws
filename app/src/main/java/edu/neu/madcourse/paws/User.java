package edu.neu.madcourse.paws;

public class User {
    String user_name;
    String nick_name;
    String profile_url;
    String city;


    public User(){

    }

    public User(String user_name,String nick_name,String profile_url, String city){
        this.user_name = user_name;
        this.nick_name = nick_name;
        this.profile_url = profile_url;
        this.city = city;
    }

    public String getUser_name(){
        return this.user_name;
    }

    public String getNick_name(){
        return  this.nick_name;
    }

    public String getProfile_url(){
        return this.profile_url;
    }

    public String getCity(){
        return this.city;
    }

}
