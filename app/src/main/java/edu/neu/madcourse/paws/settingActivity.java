package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class settingActivity<onActivityResult> extends AppCompatActivity {
    private ImageView profile_image;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button next_button = (Button) findViewById(R.id.button_next);
        EditText username_input = (EditText) findViewById(R.id.username_edit);
        ImageButton camera_button = (ImageButton) findViewById(R.id.imageButton);
        profile_image = (ImageView) findViewById(R.id.profile_setting_image);
        storageReference = FirebaseStorage.getInstance().getReference();
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromButton();
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username_input.getText().toString();
                openPetSetting();
            }
        });


    }

    public void openPetSetting(){
        Intent intent = new Intent(this,PetSettingActivity.class);
        startActivity(intent);
    }

    public void getImageFromButton(){
        /**
         * open gallery
         */
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                Log.e("image_uri",imageUri.toString());
                profile_image.setImageURI(imageUri);
                uploadImgtoFirebase(imageUri);
            }
        }
    }

    /**
     * upload image to firebase storage
     */

    private void uploadImgtoFirebase(Uri uri) {
        StorageReference fileReference = storageReference.child("profile.jpg");
        fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"image uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                Log.e("upload_reason",e.toString());
            }
        });

    }
}