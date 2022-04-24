package edu.neu.madcourse.paws;

public class Pet {
    String user_name;
    String pet_type;
    String breed;
    String age;
    String gender;

    public Pet(){

    }

    public Pet(String user_name,String pet_type,String age, String breed,String gender){
        this.user_name = user_name;
        this.pet_type = pet_type;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
    }

    public String getUser_name(){
        return this.user_name;
    }

    public String getPet_type(){
        return this.pet_type;
    }

    public String getBreed(){
        return this.breed;
    }

    public String getAge(){
        return this.age;
    }

    public String getGender(){
        return this.gender;
    }
}
