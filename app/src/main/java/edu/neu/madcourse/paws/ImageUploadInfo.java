package edu.neu.madcourse.paws;


public class ImageUploadInfo {

        public String personName;

        public String imageName;

        public String imageURL;

        public ImageUploadInfo() {

        }

        public ImageUploadInfo(String personName,String name, String url) {
            this.personName = personName;
            this.imageName = name;
            this.imageURL= url;
        }

        public String getImageName() {
            return imageName;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getPersonName(){
            return personName;
        }



}

