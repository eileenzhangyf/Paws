package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PetSettingActivity extends AppCompatActivity {
    DatabaseReference pet_db;
    FirebaseUser firebaseUser;
    String curr_user_email;
    EditText petType_et;
    EditText breed_et;
    EditText age_et;
    EditText gender_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_setting);
        pet_db = FirebaseDatabase.getInstance().getReference("pet_info");

        petType_et = (EditText) findViewById(R.id.editTextPetType);
        breed_et = (EditText) findViewById(R.id.editTextBreed);
        age_et = (EditText) findViewById(R.id.editTextAge);
        gender_et = (EditText) findViewById(R.id.editTextGender);

        Button finish_button = (Button) findViewById(R.id.button_finish);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToFirebase();
                openNav();
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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




    }

    public void openNav(){
        Intent intent = new Intent(this,PetSettingActivity2.class);
        startActivity(intent);
    }

    public void uploadToFirebase(){
        String petType = petType_et.getText().toString().trim();

        String breed = breed_et.getText().toString().trim();

        String age = age_et.getText().toString().trim();

        String gender = gender_et.getText().toString().trim();

        Pet pet = new Pet(curr_user_email,petType,age,breed,gender);
        String upload_id = pet_db.push().getKey();
        pet_db.child(upload_id).setValue(pet);
    }
}