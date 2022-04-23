package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ComposeActivity extends AppCompatActivity {
    String Storage_path = "All_images/";
    String Database_path = "All_images_db";
    private EditText post;
    private Button post_button;
    private ImageView chooseImage;
    Uri Filepath_uri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private int img_request_code = 7;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);
        post_button = (Button) findViewById(R.id.post_button);
        chooseImage = (ImageView) findViewById(R.id.select_img);
        post = (EditText) findViewById(R.id.edit_post);
        progressDialog = new ProgressDialog(ComposeActivity.this);


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "please select image"), img_request_code);
            }
        });

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageFileToFirebaseStorage();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == img_request_code){
            if(resultCode == Activity.RESULT_OK){
                Filepath_uri = data.getData();
                Log.e("image_uri",Filepath_uri.toString());
                chooseImage.setImageURI(Filepath_uri);
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void UploadImageFileToFirebaseStorage() {


        if (Filepath_uri != null) {


            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();


            StorageReference storageReference2nd = storageReference.child(Storage_path + System.currentTimeMillis() + "." + GetFileExtension(Filepath_uri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(Filepath_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempImageName = post.getText().toString().trim();

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo= new ImageUploadInfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child("pic").setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        } else {

            Toast.makeText(getApplicationContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

    }
}