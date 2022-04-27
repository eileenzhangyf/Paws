package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class settingActivity<onActivityResult> extends AppCompatActivity {
    private ImageView profile_image;
    StorageReference storageReference;
    DatabaseReference user_db;
    String storage_path ="All_profiles/";

    private String curr_user_email;
    FirebaseUser firebaseUser;
    EditText username_input;
    EditText city_input;
    private int image_request_code = 1000;
    Uri filepath;
    long mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button next_button = (Button) findViewById(R.id.button_next);
        username_input = (EditText) findViewById(R.id.username_edit);
        city_input = (EditText) findViewById(R.id.CityName_edit);
        ImageButton camera_button = (ImageButton) findViewById(R.id.imageButton);
        profile_image = (ImageView) findViewById(R.id.profile_setting_image);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mark = System.currentTimeMillis();
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromButton();
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImgtoFirebase();
                openPetSetting();
            }
        });
        firebaseUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            curr_user_email = firebaseUser.getEmail();
                            Log.e("curr_user",curr_user_email);
                        }else{
                            Log.e("get user","failed");
                        }
                    }
                });

        user_db = FirebaseDatabase.getInstance().getReference("user_info");



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
        startActivityForResult(Intent.createChooser(openGallery, "please select image"), image_request_code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == image_request_code){
            if(resultCode == Activity.RESULT_OK){
                filepath = data.getData();
                Log.e("image_uri",filepath.toString());
                profile_image.setImageURI(filepath);
              //  uploadImgtoFirebase(imageUri);
            }
        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /**
     * upload image to firebase storage
     */

    private void uploadImgtoFirebase() {
        if (filepath != null) {
            StorageReference fileReference2nd = storageReference.child(storage_path +mark+"."+ GetFileExtension(filepath));
            fileReference2nd.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String nickName = username_input.getText().toString();
                            String cityName = city_input.getText().toString();

                            String upload_id = user_db.push().getKey();

                            User user = new User(curr_user_email, nickName, uri.toString(), cityName);
                            user_db.child(upload_id).setValue(user);
                        }
                    });
                    Toast.makeText(getApplicationContext(), "image uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    Log.e("upload_reason", e.toString());
                }
            });

        }
    }
}